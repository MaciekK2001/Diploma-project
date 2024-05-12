package com.example.database.services;

import com.example.database.dtos.*;
import com.example.database.entities.Activity;
import com.example.database.entities.ActivityType;
import com.example.database.entities.AppUser;
import com.example.database.repositories.ActivityRepository;
import com.example.database.repositories.AppUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppUserRepository appUserRepository;

    private final ActivityRepository activityRepository;

    private final EntityManager entityManager;

    public UserStatsGetDTO getUserStatsDTO(UUID userId, Integer timePeriodOfActivities){

        AppUser appUserToGet = appUserRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "AppUser with id: " + userId + " wasn't found")
        );

        Integer avgCalories = activityRepository.getAverageCalories(userId, timePeriodOfActivities);

        ActivityType favType = ActivityType.valueOf(activityRepository.getFavActivityType(userId));

        Activity lastActivity = activityRepository.getLastActivity(userId);

        return UserStatsGetDTO.builder()
                .appUser(appUserToGet)
                .avgCalories(avgCalories)
                .favActivityType(favType)
                .lastActivity(lastActivity).build();
    }

    public Activity createActivity(ActivityCreateDTO activityCreateDTO){
        AppUser appUser = appUserRepository.findById(activityCreateDTO.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "AppUser with id: " +
                        activityCreateDTO.getUserId() + " wasn't found")
        );

        if(!retriveCurrentLoggedInUserId().equals(appUser.getAppUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only create activities for yourself");
        }

        return activityRepository.save(Activity.builder()
                .type(activityCreateDTO.getType())
                .time(activityCreateDTO.getTime())
                .burntCalories(activityCreateDTO.getBurntCalories())
                .appUser(appUser).build());

    }

    public void deleteActivity(UUID activityId){

        Activity activityToDelete = activityRepository.findById(activityId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityId +
                        " wasn't found"));
        if(!retriveCurrentLoggedInUserId().equals(activityToDelete.getAppUser().getAppUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your activities");
        }

        activityRepository.delete(activityToDelete);
    }


    public Activity updateActivity(ActivityUpdateDTO activityUpdateDTO){

        Activity activityToUpdate = activityRepository.findById(activityUpdateDTO.getActivityId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityUpdateDTO.getActivityId() +
                        " wasn't found"));
        if(!retriveCurrentLoggedInUserId().equals(activityToUpdate.getAppUser().getAppUserId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your activities");
        }

        activityToUpdate.setType(activityUpdateDTO.getType());
        activityToUpdate.setTime(activityUpdateDTO.getTime());
        activityToUpdate.setBurntCalories(activityUpdateDTO.getBurntCalories());

        return activityRepository.save(activityToUpdate);

    }

    public List<Activity> getActivitiesForUser(ActivityPageParamsDTO activityPageParamsDTO){

        return findActivityPageForUser(activityPageParamsDTO.getUserId(),
                activityPageParamsDTO.getFirstActivity(), activityPageParamsDTO.getLastActivity(),
                activityPageParamsDTO.getSortBy(),
                activityPageParamsDTO.getSortOrder(),
                activityPageParamsDTO.getConditions());
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

        for(int i = 0; i < contentToMap.size(); i++){
            AppUser appUser = (AppUser) contentToMap.get(i).get("appUser");
            AppUserBasicDataDTO appUserBasicDataDTO = AppUserBasicDataDTO.builder()
                    .email(appUser.getEmail())
                    .firstName(appUser.getFirstName())
                    .lastName(appUser.getLastName())
                    .userId(appUser.getAppUserId()).build();
            Long sumCalories = (Long) contentToMap.get(i).get("sumCalories");
            UserRankingDTO userRankingDTO = UserRankingDTO.builder()
                    .appUserBasicDataDTO(appUserBasicDataDTO)
                    .burntCalories(sumCalories).build();
            listToReturn.add(userRankingDTO);
        }

        return listToReturn;
    }

    private List<Activity> findActivityPageForUser(UUID userId, int firstActivity, int lastActivity,
                                                   String sortBy, Sort.Direction sortOrder, List<String> conditions) {
        String jpqlQuery = "SELECT a FROM Activity a WHERE a.appUser.appUserId = :userId";

        if(!(conditions == null)) {
            for (String condition : conditions) {
                jpqlQuery += " AND " + condition;
            }
        }

        jpqlQuery += " ORDER BY a." + sortBy + " " + sortOrder;

        Query query = entityManager.createQuery(jpqlQuery);
        query.setParameter("userId", userId);
        query.setFirstResult(firstActivity);
        query.setMaxResults(lastActivity - firstActivity + 1);

        return query.getResultList();
    }

    private UUID retriveCurrentLoggedInUserId(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = appUserRepository.findAppUserByEmail(email);

        return appUser.getAppUserId();
    }

}
