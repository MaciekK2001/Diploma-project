package com.example.database.controllers;

import com.example.database.dtos.ActivityCreateDTO;
import com.example.database.dtos.ActivityPageParamsDTO;
import com.example.database.dtos.ActivityUpdateDTO;
import com.example.database.dtos.UserStatsGetDTO;
import com.example.database.entities.Activity;
import com.example.database.entities.AuthenticationResponse;
import com.example.database.entities.User;
import com.example.database.services.AppService;
import com.example.database.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/api/users/{userId}")
    public UserStatsGetDTO getUser(@PathVariable UUID userId,
                                   @RequestParam Integer timePeriodOfActivities){

        return appService.getMyUserDTO(userId, timePeriodOfActivities);
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

    @PutMapping("/api/activities")
    @ResponseStatus(HttpStatus.OK)
    public Activity updateActivity(@RequestBody ActivityUpdateDTO activityUpdateDTO){
        return appService.updateActivity(activityUpdateDTO);
    }

    @GetMapping("/api/activities")
    public List<Activity> getActivities(@RequestBody ActivityPageParamsDTO activityPageParamsDTO){
        return appService.getActivitiesForUser(activityPageParamsDTO);
    }

}
