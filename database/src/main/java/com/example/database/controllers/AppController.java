package com.example.database.controllers;

import com.example.database.dtos.*;
import com.example.database.entities.Activity;
import com.example.database.entities.AuthenticationResponse;
import com.example.database.entities.User;
import com.example.database.services.AppService;
import com.example.database.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AppController {

    private final AppService appService;

    private final AuthenticationService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserCreateDTO request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/api/users")
    public UserStatsGetDTO getUser(@RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "1000000") Integer timePeriodOfActivities){

        return appService.getUserStatsDTO(username, timePeriodOfActivities);
    }

    @PostMapping("/api/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public Activity createActivity(@RequestBody ActivityCreateDTO activityCreateDTO){
        return appService.createActivity(activityCreateDTO);
    }

    @DeleteMapping("/api/activities/{activityId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteActivity(@PathVariable UUID activityId){
        return appService.deleteActivity(activityId);
    }

    @PutMapping("/api/activities/{activityId}")
    @ResponseStatus(HttpStatus.OK)
    public Activity updateActivity(@RequestBody ActivityCreateDTO activityCreateDTO,
                                    @PathVariable UUID activityId){
        return appService.updateActivity(activityCreateDTO, activityId);
    }

    @GetMapping("/api/activities")
    public List<Activity> getActivities(@ModelAttribute ActivityPageParams activityPageParams){
        return appService.getActivitiesForUser(activityPageParams, null);
    }

    @GetMapping("/api/activities/{userId}")
    public List<Activity> getActivities(@ModelAttribute ActivityPageParams activityPageParams,
                                        @PathVariable UUID userId){
        return appService.getActivitiesForUser(activityPageParams, userId);
    }

    @GetMapping("/api/users/ranking")
    public List<UserRankingDTO> getRanking(@RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam Sort.Direction sortDirection){

        return appService.getUsersRanking(pageNumber, pageSize, sortDirection);
    }

    @GetMapping("/api/userBasicData")
    public UserBasicDataDTO getUserData(){
        return appService.getUserDataDTO();
    }


}
