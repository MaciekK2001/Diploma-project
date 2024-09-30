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
            @RequestBody User request
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
    public UserStatsGetDTO getUser(@RequestParam(defaultValue = "") String email,
                                   @RequestParam(defaultValue = "1000000") Integer timePeriodOfActivities){

        return appService.getUserStatsDTO(email, timePeriodOfActivities);
    }

    @PostMapping("/api/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public Activity createActivity(@RequestBody ActivityCreateDTO activityCreateDTO){
        return appService.createActivity(activityCreateDTO);
    }

    @DeleteMapping("/api/activities/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActivity(@PathVariable UUID activityId){
        appService.deleteActivity(activityId);
    }

    @PutMapping("/api/activities/{activityId}")
    @ResponseStatus(HttpStatus.OK)
    public Activity updateActivity(@RequestBody ActivityUpdateDTO activityUpdateDTO,
                                    @PathVariable UUID activityId){
        return appService.updateActivity(activityUpdateDTO, activityId);
    }

    @PostMapping("/api/activities/{userId}")//sprawdzic
    public List<Activity> getActivities(@RequestBody ActivityPageParamsDTO activityPageParamsDTO,
                                        @PathVariable UUID userId){
        return appService.getActivitiesForUser(activityPageParamsDTO, userId);
    }

    @GetMapping("/api/users/ranking")
    public List<UserRankingDTO> getRanking(@RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam Sort.Direction sortDirection){

        return appService.getUsersRanking(pageNumber, pageSize, sortDirection);
    }

}
