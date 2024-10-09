package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.*;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarRepository extends JpaRepository<Star, Long> {
    Star findByUserAndFive(User user, Five five);
    Star findByUserAndTen(User user, Ten ten);
    Star findByUserAndFifteen(User user, Fifteen fifteen);

    //long countByStory5(Story5 story5);

    List<Star> findTop4ByUserOrderByCreatedAtDesc(User user);



}
