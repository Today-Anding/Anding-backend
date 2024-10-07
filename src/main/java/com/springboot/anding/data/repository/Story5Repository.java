package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.Story5;
import com.springboot.anding.data.entity.synopsis.Five;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
public interface Story5Repository extends JpaRepository<Story5,Long> {

    long countByFive(Five five);
    //Optional<Story5> findByFive_IdAndPosition(Long five_id, int position);

    @Query("SELECT s FROM Story5 s WHERE s.five.five_id = :five_id AND s.position = :position")
    Optional<Story5> findByFiveIdAndPosition(@Param("five_id") Long five_id, @Param("position") int position);

    @Query("SELECT s FROM Story5 s WHERE s.five = :five AND s.five.finished = :finished")
    List<Story5> findByFiveAndFinished(@Param("five") Five five, @Param("finished") boolean finished);

    @Query(value = "SELECT * FROM story5 WHERE five_id = :fiveId ORDER BY story5_id DESC LIMIT 1", nativeQuery = true)
    Optional<Story5> findMostRecentStory5ByFiveId(@Param("fiveId") Long fiveId);


    // @Query("SELECT s FROM Story5 s WHERE s.five.five_id = :five_id AND s.story5_id = :story5_id")
   // Optional<Story5> findByFiveIdAndStory5Id(@Param("five_id") Long five_id, @Param("story5_id") Long story5_id);
}
