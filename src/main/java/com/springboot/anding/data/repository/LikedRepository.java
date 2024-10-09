package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.*;
import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Liked findByUserAndFive(User user, Five five);
    Liked findByUserAndTen(User user, Ten ten);
    Liked findByUserAndFifteen(User user, Fifteen fifteen);

    long countByFive(Five five);
    long countByTen(Ten ten);
    long countByFifteen(Fifteen fifteen);


    Liked findByUserAndWriter(User user, String writer);

    Liked findByUserAndStory5(User user, Story5 story5);

    Liked findByUserAndStory10(User user, Story10 story10);
}
