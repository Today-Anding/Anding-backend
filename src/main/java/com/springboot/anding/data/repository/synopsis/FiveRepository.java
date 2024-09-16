package com.springboot.anding.data.repository.synopsis;

import com.springboot.anding.data.entity.synopsis.Five;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FiveRepository extends JpaRepository<Five, Long> {
    List<Five> findByFinishedTrue();
}
