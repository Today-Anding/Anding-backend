package com.springboot.anding.data.entity;
import com.springboot.anding.data.entity.synopsis.Five;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "story5")
public class Story5 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long story5_id;
    @Column(unique = true, nullable = false)
    private String content;

    @Column(nullable = false)
    private int position = 0;


    @ManyToOne
    @JoinColumn(name="uid")
    @ToString.Exclude  //순환참조 방지
    private User user;

    @ManyToOne
    @JoinColumn(name="five_id")
    @ToString.Exclude  //순환참조 방지
    private Five five;

}
