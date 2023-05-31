package com.project.carpool.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Star {
    @Id
    @Column(name = "STAR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long point;
    private Long number;

    public void updateStar(Long point){
        this.point=(this.point+point)/(this.number+1);
    }

    @Builder
    public Star(Long point, Long number) {
        this.point = point;
        this.number = number;
    }
}
