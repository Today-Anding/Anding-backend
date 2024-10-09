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

    @Column(nullable = true)
    private String title;
    @Column(nullable = true)
    private String writer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "five_id")
    private Five five;

    @ManyToOne
    @JoinColumn(name = "story5_id")
    private Story5 story5;

    @ManyToOne
    @JoinColumn(name = "story10_id")
    private Story10 story10;

    @ManyToOne
    @JoinColumn(name = "story15_id")
    private Story15 story15;
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
