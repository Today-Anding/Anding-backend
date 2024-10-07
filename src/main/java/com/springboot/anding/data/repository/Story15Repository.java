package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.Story15;
import com.springboot.anding.data.entity.Story5;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Story15Repository extends JpaRepository<Story15,Long> {

    long countByFifteen(Fifteen fifteen);
    @Query("SELECT s FROM Story15 s WHERE s.fifteen = :fifteen AND s.fifteen.finished = :finished")
    List<Story15> findByFifteenAndFinished(@Param("fifteen") Fifteen fifteen, @Param("finished") boolean finished);


    @Query("SELECT s FROM Story15 s WHERE s.fifteen.fifteen_id = :fifteen_id AND s.position = :position")
    Optional<Story15> findByFifteenIdAndPosition(@Param("fifteen_id") Long fifteen_id, @Param("position") int position);

    @Query(value = "SELECT * FROM story15 WHERE fifteen_id = :fifteenId ORDER BY story15_id DESC LIMIT 1", nativeQuery = true)
    Optional<Story15> findMostRecentStory15ByFifteenId(@Param("fifteenId") Long fifteenId);

    //@Query("SELECT s FROM Story15 s WHERE s.fifteen.fifteen_id = :fifteen_id AND s.story15_id = :story15_id")
    //Optional<Story15> findByFifteenIdAndStory15Id(@Param("fifteen_id") Long fifteen_id, @Param("story15_id") Long story15_id);

}
