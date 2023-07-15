package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Entity
@Table(name = "FcmLog")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class FcmLog extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", nullable = false)
    private User user;

    @Column(nullable = false)
    private String type;

    @Column
    private String data;

    @Builder
    public FcmLog(
            User user,
            String type,
            String data
    ) {
        this.user = user;
        this.type = type;
        this.data = data;
    }
}

