package com.example.database.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserCreateDTO {

    String username;

    String firstName;

    String lastName;

    String about;

    String email;

    String password;
}
