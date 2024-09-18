package com.springboot.anding.data.repository.synopsis;

import com.springboot.anding.data.entity.synopsis.Ten;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenRepository extends JpaRepository<Ten, Long> {
    List<Ten> findByFinishedTrue();

}
