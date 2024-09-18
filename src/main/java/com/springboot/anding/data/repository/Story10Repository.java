package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.Story10;
import com.springboot.anding.data.entity.synopsis.Ten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Story10Repository extends JpaRepository<Story10, Long> {
    long countByTen(Ten ten);
    @Query("SELECT s FROM Story10 s WHERE s.ten = :ten AND s.ten.finished = :finished")
    List<Story10> findByTenAndFinished(@Param("ten") Ten ten, @Param("finished") boolean finished);

    @Query("SELECT s FROM Story10 s WHERE s.ten.ten_id = :ten_id AND s.position = :position")
    Optional<Story10> findByTenIdAndPosition(@Param("ten_id") Long ten_id, @Param("position") int position);

    //@Query("SELECT s FROM Story10 s WHERE s.ten.ten_id = :ten_id AND s.story10_id = :story10_id")
    //Optional<Story10> findByTenIdAndStory10Id(@Param("ten_id") Long ten_id, @Param("story10_id") Long story10_id);
}
