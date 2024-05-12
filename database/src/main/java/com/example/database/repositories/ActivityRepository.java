package com.example.database.repositories;

import com.example.database.entities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    @Query(value = "SELECT AVG(a.burnt_calories) FROM Activity a WHERE a.app_user_id = ?1 " +
            "AND a.created_at >= NOW() - ?2 * INTERVAL '1 day'", nativeQuery = true)
    Integer getAverageCalories(UUID userId, Integer timePeriod);

    @Query(value = "SELECT a.type " +
            "FROM activity a WHERE a.app_user_id = :userId " +
            "GROUP BY type " +
            "ORDER BY COUNT(a.activity_id) DESC " +
            "FETCH FIRST 1 ROW ONLY;", nativeQuery = true)
    String getFavActivityType(UUID userId);

    @Query(value = "SELECT * FROM Activity a WHERE a.app_user_id = ?1" +
            " ORDER BY a.created_at DESC " +
            "FETCH FIRST 1 ROW ONLY",nativeQuery = true)
    Activity getLastActivity(UUID userId);

    @Query(value = "SELECT  a.appUser AS appUser,  SUM(a.burntCalories) AS sumCalories FROM Activity a " +
            "GROUP BY a.appUser")
    Page<Map<String, Object>> getActivityUsersRanking(Pageable pageable);

}
