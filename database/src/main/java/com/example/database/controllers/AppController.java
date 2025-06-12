package com.example.database.controllers;

import com.example.database.utils.CustomLogoutHandler;
import com.example.database.dtos.*;
import com.example.database.entities.Activity;
import com.example.database.dtos.AuthenticationResponse;
import com.example.database.entities.User;
import com.example.database.services.AppService;
import com.example.database.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AppController {

    private final AppService appService;

    private final AuthenticationService authService;

    private final CustomLogoutHandler logoutHandler;

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

    @PutMapping("/api/changePassword")
    public MessageResponseDTO changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        authService.changePassword(changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());

        return MessageResponseDTO.builder().message("Successfully changed password").build();
    }

    @PostMapping("/api/logout")
    public MessageResponseDTO logout(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logoutHandler.logout(request, response, authentication);
        SecurityContextHolder.clearContext();

        return MessageResponseDTO.builder().message("Successfully logged out").build();
    }

    @GetMapping("/api/getDailyStatistics")
    public List<DailyStatisticsDTO> getDailyStatistics(@RequestBody StatisticsSearchParams statisticsSearchParams) {
        return appService.getStatisticsDaily(statisticsSearchParams.getStatisticsPeriod());
    }

    @GetMapping("/api/getYearlyStatistics")
    public List<MonthlyStatisticsDTO> getYearlyStatistics() {
        return appService.getStatisticsYearly();
    }

}
