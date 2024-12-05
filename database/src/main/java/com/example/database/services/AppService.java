package com.example.database.services;

import com.example.database.dtos.*;
import com.example.database.entities.*;
import com.example.database.entities.QActivity;
import com.example.database.repositories.ActivityRepository;
import com.example.database.repositories.UserRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;

@Service
@RequiredArgsConstructor
public class AppService {

    private final UserRepository userRepository;

    private final ActivityRepository activityRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public UserStatsGetDTO getUserStatsDTO(String email, Integer timePeriodOfActivities){

        User userToGet;

        if(Objects.equals(email, "") || email.isEmpty()){
            userToGet = retriveCurrentLoggedInUser();
        }
        else {
            userToGet = userRepository.findUserByEmail(email).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "AppUser with email: " + email + " wasn't found")
            );
        }

        UUID userId = userToGet.getUserId();

        Integer avgCalories = activityRepository.getAverageCalories(userId, timePeriodOfActivities);

        String stringFavType = activityRepository.getFavActivityType(userId);

        ActivityType favType = null;

        if(stringFavType != null) {
            favType = ActivityType.valueOf(activityRepository.getFavActivityType(userId));
        }
        Activity lastActivity = activityRepository.getLastActivity(userId);

        return UserStatsGetDTO.builder()
                .user(userToGet)
                .avgCalories(avgCalories)
                .favActivityType(favType)
                .lastActivity(lastActivity).build();
    }

    public Activity createActivity(ActivityCreateDTO activityCreateDTO){

        UUID userId = retriveCurrentLoggedInUserId();
        User user = userRepository.findUserByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " +
                        userId + " wasn't found")
        );

        if(!retriveCurrentLoggedInUserId().equals(user.getUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only create activities for yourself");
        }

        return activityRepository.save(Activity.builder()
                .activityType(activityCreateDTO.getType())
                .time(activityCreateDTO.getTime())
                .burntCalories(activityCreateDTO.getBurntCalories())
                .user(user).build());

    }

    public void deleteActivity(UUID activityId){

        Activity activityToDelete = activityRepository.findById(activityId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityId +
                        " wasn't found"));
        if(!retriveCurrentLoggedInUserId().equals(activityToDelete.getUser().getUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your activities");
        }

        activityRepository.delete(activityToDelete);
    }


    public Activity updateActivity(ActivityUpdateDTO activityUpdateDTO, UUID activityId){

        Activity activityToUpdate = activityRepository.findById(activityId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityId +
                        " wasn't found"));
        if(!retriveCurrentLoggedInUserId().equals(activityToUpdate.getUser().getUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your activities");
        }

        activityToUpdate.setActivityType(activityUpdateDTO.getType());
        activityToUpdate.setTime(activityUpdateDTO.getTime());
        activityToUpdate.setBurntCalories(activityUpdateDTO.getBurntCalories());

        return activityRepository.save(activityToUpdate);

    }


    public List<UserRankingDTO> getUsersRanking(int pageNumber, int pageSize, Sort.Direction sortDirection){
        Sort sort = Sort.by(sortDirection, "sumCalories");
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Map<String, Object>> page = activityRepository.getActivityUsersRanking(pageRequest);

        List<Map<String, Object>> contentToMap = page.getContent();

        return mapToUserRankingDTO(contentToMap);
    }

    private List<UserRankingDTO> mapToUserRankingDTO(List<Map<String, Object>> contentToMap){
        List<UserRankingDTO> listToReturn = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : contentToMap) {
            User user = (User) stringObjectMap.get("user");
            AppUserBasicDataDTO appUserBasicDataDTO = AppUserBasicDataDTO.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userId(user.getUserId()).build();
            Long sumCalories = (Long) stringObjectMap.get("sumCalories");
            UserRankingDTO userRankingDTO = UserRankingDTO.builder()
                    .appUserBasicDataDTO(appUserBasicDataDTO)
                    .burntCalories(sumCalories).build();
            listToReturn.add(userRankingDTO);
        }

        return listToReturn;
    }

    public List<Activity> getActivitiesForUser(ActivityPageParamsDTO activityPageParamsDTO, UUID userId){

        return findActivityPageForUser(userId,
                activityPageParamsDTO.getPageSize(),
                activityPageParamsDTO.getPageNumber(),
                activityPageParamsDTO.getSortBy(),
                activityPageParamsDTO.getSortOrder(),
                activityPageParamsDTO.getConditions());
    }


    private List<Activity> findActivityPageForUser(UUID userId, int pageSize, int pageNumber,
                                                   String sortBy, Sort.Direction sortOrder, List<ActivityType> conditionsActivityType) {

        PathBuilder<Activity> pathBuilder = new PathBuilder<>(Activity.class, "activity");
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Querydsl querydsl = new Querydsl(entityManager, pathBuilder);
        QActivity activity = QActivity.activity;

        if(conditionsActivityType == null){
            conditionsActivityType = new ArrayList<>();
            conditionsActivityType.add(ActivityType.RUN);
            conditionsActivityType.add(ActivityType.DANCE);
            conditionsActivityType.add(ActivityType.SWIM);
            conditionsActivityType.add(ActivityType.WORKOUT);
            conditionsActivityType.add(ActivityType.TEAM_SPORT);
        }

        OrderSpecifier<?> orderSpecifier;
        if (sortOrder == Sort.Direction.ASC) {
            orderSpecifier = pathBuilder.getString(sortBy).asc();
        } else {
            orderSpecifier = pathBuilder.getString(sortBy).desc();
        }

        JPAQuery<Activity> query = new JPAQuery<>(entityManager).select(activity)
                .from(activity)
                .where(activity.user.userId.eq(userId)
                        .and(activity.activityType.in(conditionsActivityType)))
                .orderBy(orderSpecifier);

        return querydsl.applyPagination(pageRequest, query).fetch();
    }

    private UUID retriveCurrentLoggedInUserId(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email: " + email + " wasn't found")
        );

        return user.getUserId();
    }

    private User retriveCurrentLoggedInUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email: " + email + " wasn't found")
        );

        return user;
    }


}
