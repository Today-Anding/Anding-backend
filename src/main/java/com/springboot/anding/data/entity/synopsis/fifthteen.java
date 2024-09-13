package com.springboot.anding.data.entity.synopsis;

import com.springboot.anding.data.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fifthteen")
public class fifthteen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fifthteen_id;
    @Column(unique = true, nullable = false) //시놉시스 제목
    private String title;
    @Column(nullable = false) //시놉시스 소개
    private String description;
    @Column(nullable = false) //시놉시스 본문
    private String content;
    @Column(nullable = false) //표지
    private String thumbnail;
    @Column(nullable = false)
    private boolean is_finished; //이야기 완결 확인용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    @ToString.Exclude  //순환참조 방지
    private User user;
}
