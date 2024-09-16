package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.Story15;
import com.springboot.anding.data.entity.Story5;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Story15Repository extends JpaRepository<Story15,Long> {

    long countByFifteen(Fifteen fifteen);

    @Query("SELECT s FROM Story15 s WHERE s.fifteen.fifteen_id = :fifteen_id AND s.story15_id = :story15_id")
    Optional<Story15> findByFifteenIdAndStory15Id(@Param("fifteen_id") Long fifteen_id, @Param("story15_id") Long story15_id);

}
