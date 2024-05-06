package com.example.database.repositories;

import com.example.database.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    AppUser findAppUserByEmail(String email);
}
