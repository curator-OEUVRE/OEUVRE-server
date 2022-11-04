package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "Block")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Block extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blockUserNo", nullable = false)
    private User blockUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blockedUserNo", nullable = false)
    private User blockedUser;

    @Builder
    public Block(
            Long no,
            User blockUser,
            User blockedUser
    ) {
        this.no = no;
        this.blockUser = blockUser;
        this.blockedUser = blockedUser;
    }
}

