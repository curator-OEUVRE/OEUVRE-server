package com.curator.oeuvre.domain;

import io.swagger.models.auth.In;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "FloorRead")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class FloorRead extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floorNo", nullable = false)
    private Floor floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", nullable = false)
    private User user;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isNew;

    @Column(nullable = false)
    private Integer updateCount;

    @Builder
    public FloorRead(
            Long no,
            Floor floor,
            User user,
            Boolean isNew,
            Integer updateCount
    ) {
        this.no = no;
        this.floor = floor;
        this.user = user;
        this.isNew = isNew;
        this.updateCount = updateCount;
    }
}

