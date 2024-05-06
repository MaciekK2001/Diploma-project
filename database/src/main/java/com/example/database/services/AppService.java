package com.example.database.services;

import com.example.database.dtos.ActivityCreateDTO;
import com.example.database.dtos.ActivityPageParamsDTO;
import com.example.database.dtos.ActivityUpdateDTO;
import com.example.database.dtos.UserStatsGetDTO;
import com.example.database.entities.Activity;
import com.example.database.entities.ActivityType;
import com.example.database.entities.AppUser;
import com.example.database.repositories.ActivityRepository;
import com.example.database.repositories.AppUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppUserRepository appUserRepository;

    private final ActivityRepository activityRepository;

    private final EntityManager entityManager;

    public UserStatsGetDTO getMyUserDTO(UUID userId, Integer timePeriodOfActivities){

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

        return activityRepository.save(Activity.builder()
                .type(activityCreateDTO.getType())
                .time(activityCreateDTO.getTime())
                .burntCalories(activityCreateDTO.getBurntCalories())
                .appUser(appUser).build());

        //TODO check if i create an activity for myself (compare userId with session userId)

    }

    public void deleteActivity(UUID activityId){
        //todo: check if session user owns the activity

        Activity activityToDelete = activityRepository.findById(activityId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityId +
                        " wasn't found"));

        activityRepository.delete(activityToDelete);
    }


    public Activity updateActivity(ActivityUpdateDTO activityUpdateDTO){

        Activity activityToUpdate = activityRepository.findById(activityUpdateDTO.getActivityId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with id: " + activityUpdateDTO.getActivityId() +
                        " wasn't found"));

        activityToUpdate.setType(activityUpdateDTO.getType());
        activityToUpdate.setTime(activityUpdateDTO.getTime());
        activityToUpdate.setBurntCalories(activityUpdateDTO.getBurntCalories());

        return activityRepository.save(activityToUpdate);

        //TODO check if i create an activity for myself (compare userId with session userId)

    }

    public List<Activity> getActivitiesForUser(ActivityPageParamsDTO activityPageParamsDTO){
        //todo: same thing - session

        return findActivityPageForUser(activityPageParamsDTO.getUserId(),
                activityPageParamsDTO.getFirstActivity(), activityPageParamsDTO.getLastActivity(),
                activityPageParamsDTO.getSortBy(),
                activityPageParamsDTO.getSortOrder(),
                activityPageParamsDTO.getConditions());
    }

    private List<Activity> findActivityPageForUser(UUID userId, int firstActivity, int lastActivity,
                                                   String sortBy, Sort.Direction sortOrder, List<String> conditions) {
        String jpqlQuery = "SELECT a FROM Activity a WHERE a.appUser.appUserId = :userId";

        for(String condition : conditions){
            jpqlQuery += " AND " + condition;
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
