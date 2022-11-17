package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "SystemConstant")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class SystemConstant extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isGuestLoginAvailable;

    @Builder
    public SystemConstant(
            Long no,
            Boolean isGuestLoginAvailable
    ) {
        this.no = no;
        this.isGuestLoginAvailable = isGuestLoginAvailable;
    }
}


