package com.example.database.services;

import com.example.database.dtos.*;
import com.example.database.entities.*;
import com.example.database.entities.QActivity;
import com.example.database.mappers.UserMapper;
import com.example.database.repositories.ActivityRepository;
import com.example.database.repositories.UserRepository;
import com.querydsl.core.BooleanBuilder;
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

    private final UserMapper userMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    public UserStatsGetDTO getUserStatsDTO(String username, Integer timePeriodOfActivities){

        User userToGet;

        if(Objects.equals(username, "") || username.isEmpty()){
            userToGet = retriveCurrentLoggedInUser();
        }
        else {
            userToGet = userRepository.findByUsername(username).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "AppUser with email: " + username + " wasn't found")
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
                .userDTO(userMapper.mapUserToUserDTO(userToGet))
                .avgCalories(avgCalories)
                .favActivityType(favType)
                .lastActivity(lastActivity).build();
    }

    public UserBasicDataDTO getUserDataDTO(){
        User userToGet = retriveCurrentLoggedInUser();

        return UserBasicDataDTO.builder()
                .userId(userToGet.getUserId())
                .username(userToGet.getUsername()).build();
    }

    public Activity createActivity(ActivityCreateDTO activityCreateDTO){

        UUID userId = retrieveCurrentLoggedInUserId();
        User user = userRepository.findUserByUserId(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " +
                        userId + " wasn't found")
        );

        if(!retrieveCurrentLoggedInUserId().equals(user.getUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only create activities for yourself");
        }

        return activityRepository.save(Activity.builder()
                .activityType(activityCreateDTO.getType())
                .time(activityCreateDTO.getTime())
                .burntCalories(activityCreateDTO.getBurntCalories())
                .user(user).build());

    }

    public String deleteActivity(UUID activityId){

        Activity activityToDelete = activityRepository.findById(activityId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityId +
                        " wasn't found"));
        if(!retrieveCurrentLoggedInUserId().equals(activityToDelete.getUser().getUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your activities");
        }

        activityRepository.delete(activityToDelete);
        return "Activity deleted successfully";
    }


    public Activity updateActivity(ActivityCreateDTO activityCreateDTO, UUID activityId){

        Activity activityToUpdate = activityRepository.findById(activityId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityId +
                        " wasn't found"));
        if(!retrieveCurrentLoggedInUserId().equals(activityToUpdate.getUser().getUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your activities");
        }

        activityToUpdate.setActivityType(activityCreateDTO.getType());
        activityToUpdate.setTime(activityCreateDTO.getTime());
        activityToUpdate.setBurntCalories(activityCreateDTO.getBurntCalories());

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
                    .userId(user.getUserId())
                    .username(user.getUsername()).build();
            Long sumCalories = (Long) stringObjectMap.get("sumCalories");
            UserRankingDTO userRankingDTO = UserRankingDTO.builder()
                    .appUserBasicDataDTO(appUserBasicDataDTO)
                    .burntCalories(sumCalories).build();
            listToReturn.add(userRankingDTO);
        }

        return listToReturn;
    }

    public List<Activity> getActivitiesForUser(ActivityPageParams activityPageParams, UUID userId){

        return findActivityPageForUser(userId,
                activityPageParams.getPageSize(),
                activityPageParams.getPageNumber(),
                activityPageParams.getSortBy(),
                activityPageParams.getSortOrder(),
                activityPageParams.getConditions());
    }


    private List<Activity> findActivityPageForUser(UUID userId, int pageSize, int pageNumber,
                                                   String sortBy, Sort.Direction sortOrder, List<ActivityType> conditionsActivityType) {

        if(userId == null){
            userId = retrieveCurrentLoggedInUserId();
        }
        PathBuilder<Activity> pathBuilder = new PathBuilder<>(Activity.class, "activity");
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Querydsl querydsl = new Querydsl(entityManager, pathBuilder);
        QActivity activity = QActivity.activity;
        BooleanBuilder conditions = new BooleanBuilder();

        conditions.and(activity.user.userId.eq(userId));
        if(conditionsActivityType != null){
            conditions.and(activity.activityType.in(conditionsActivityType));
        }

        JPAQuery<Activity> query = new JPAQuery<>(entityManager).select(activity)
                .from(activity)
                .where(conditions);

        OrderSpecifier<?> orderSpecifier;

        if(sortBy != null) {
            if (sortOrder == Sort.Direction.ASC) {
                orderSpecifier = pathBuilder.getString(sortBy).asc();
            } else {
                orderSpecifier = pathBuilder.getString(sortBy).desc();
            }
            query.orderBy(orderSpecifier);

        }

        if(conditionsActivityType != null){
            query.where(activity.user.userId.eq(userId)
                    .and(activity.activityType.in(conditionsActivityType)));
        } else {
            query.where(activity.user.userId.eq(userId));
        }

        return querydsl.applyPagination(pageRequest, query).fetch();
    }

    private UUID retrieveCurrentLoggedInUserId(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username: " + username + " wasn't found")
        );

        return user.getUserId();
    }

    private User retriveCurrentLoggedInUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username: " + username + " wasn't found")
        );
    }


}
