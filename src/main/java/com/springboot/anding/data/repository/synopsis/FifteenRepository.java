package com.springboot.anding.data.repository.synopsis;

import com.springboot.anding.data.entity.synopsis.Fifteen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FifteenRepository extends JpaRepository<Fifteen,Long> {
    List<Fifteen> findByFinishedTrue();
}
