package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "Scrap")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Scrap extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pictureNo", nullable = false)
    private Picture picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", nullable = false)
    private User user;

    @Builder
    public Scrap (
            Long no,
            Picture picture,
            User user
    ) {
        this.no = no;
        this.picture = picture;
        this.user = user;
    }
}
