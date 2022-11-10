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
@Table(name = "Notification")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Notification extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", nullable = false)
    private User user;

    @Column(nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sendUserNo", nullable = false)
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentNo", nullable = true)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likeNo", nullable = true)
    private Likes likes;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRead;

    @Builder
    public Notification(
            Long no,
            User user,
            String type,
            User sendUser,
            Comment comment,
            Likes likes,
            Boolean isRead
    ) {
        this.no = no;
        this.user = user;
        this.type = type;
        this.sendUser = sendUser;
        this.comment = comment;
        this.likes = likes;
        this.isRead = isRead;
    }
}
