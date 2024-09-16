package com.springboot.anding.data.entity.synopsis;

import com.springboot.anding.data.entity.Story5;
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
@Table(name = "five")
public class Five {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long five_id;
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

    @OneToMany(mappedBy = "five", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Story5> stories = new ArrayList<>();

}
