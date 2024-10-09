package com.springboot.anding.data.entity;

import com.springboot.anding.data.entity.synopsis.Fifteen;
import com.springboot.anding.data.entity.synopsis.Five;
import com.springboot.anding.data.entity.synopsis.Ten;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "liked")
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liked_id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "five_id")
    private Five five;

    @ManyToOne
    @JoinColumn(name = "ten_id")
    private Ten ten;

    @ManyToOne
    @JoinColumn(name = "fifteen_id")
    private Fifteen fifteen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType likeType;

}
