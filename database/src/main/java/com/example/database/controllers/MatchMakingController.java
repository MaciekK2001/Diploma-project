package com.example.database.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MatchMakingController {

    private final SimpMessageSendingOperations messagingTemplate;



}