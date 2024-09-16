package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.Story5;
import com.springboot.anding.data.entity.synopsis.Five;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Story5Repository extends JpaRepository<Story5,Long> {

    long countByFive(Five five);

    @Query("SELECT s FROM Story5 s WHERE s.five.five_id = :five_id AND s.story5_id = :story5_id")
    Optional<Story5> findByFiveIdAndStory5Id(@Param("five_id") Long five_id, @Param("story5_id") Long story5_id);
}
