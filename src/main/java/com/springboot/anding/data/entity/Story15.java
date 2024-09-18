package com.springboot.anding.data.entity;

import com.springboot.anding.data.entity.synopsis.Fifteen;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "story15")
public class Story15 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long story15_id;
    @Column(unique = true, nullable = false)
    private String content;

    @Column(nullable = false)
    private int position;

    @ManyToOne
    @JoinColumn(name="uid")
    @ToString.Exclude  //순환참조 방지
    private User user;

    @ManyToOne
    @JoinColumn(name="fifteen_id")
    @ToString.Exclude  //순환참조 방지
    private Fifteen fifteen;
}
