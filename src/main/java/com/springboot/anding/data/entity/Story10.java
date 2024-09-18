package com.springboot.anding.data.entity;

import com.springboot.anding.data.entity.synopsis.Ten;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "story10")
public class Story10 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long story10_id;
    @Column(unique = true, nullable = false)
    private String content;
    @Column(nullable = false)
    private int position;

    @ManyToOne
    @JoinColumn(name="uid")
    @ToString.Exclude  //순환참조 방지
    private User user;

    @ManyToOne
    @JoinColumn(name="ten_id")
    @ToString.Exclude  //순환참조 방지
    private Ten ten;
}
