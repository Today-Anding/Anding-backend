package com.springboot.anding.data.entity.synopsis;

import com.springboot.anding.data.entity.Story10;
import com.springboot.anding.data.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ten")
public class Ten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ten_id;
    @Column(unique = true, nullable = false) //시놉시스 제목
    private String title;
    @Column(nullable = false) //시놉시스 소개
    private String description;
    @Column(nullable = false) //시놉시스 본문
    private String content;
    @Column(nullable = false) //표지
    private String thumbnail;
    @Column(nullable = false)
    private boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    @ToString.Exclude  //순환참조 방지
    private User user;

    @OneToMany(mappedBy = "ten", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Story10> stories = new ArrayList<>();

}
