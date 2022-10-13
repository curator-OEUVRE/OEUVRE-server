package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "Following")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Following extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_no", nullable = false)
    private User followUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_user_no", nullable = false)
    private User followedUser;

    @Builder
    public Following (
            Long no,
            User followUser,
            User followedUser
    ) {
        this.no = no;
        this.followUser = followUser;
        this.followedUser = followedUser;
    }
}
