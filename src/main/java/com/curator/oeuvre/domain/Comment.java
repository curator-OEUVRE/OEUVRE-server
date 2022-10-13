package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "Comment")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Comment extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floorNo", nullable = false)
    private Floor floor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userNo", nullable = false)
    private User user;

    @Column(nullable = false)
    private String comment;

    @Builder
    public Comment (
            Long no,
            Floor floor,
            User user,
            String comment
    ) {
        this.no = no;
        this.floor = floor;
        this.user = user;
        this.comment = comment;
    }
}
