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
@Table(name = "Floor")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Floor extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer queue;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ColumnDefault(value = "#FFFFFF")
    private String color;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    private Integer texture;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    private Boolean isPublic;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    private Boolean isCommentAvailable;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private Boolean isGroupExhibition;

    @Builder
    public Floor(
            Long no,
            User user,
            Integer queue,
            String name,
            String color,
            Integer texture,
            Boolean isPublic,
            Boolean isCommentAvailable,
            Boolean isGroupExhibition
    ) {
        this.no = no;
        this.user = user;
        this.queue = queue;
        this.name = name;
        this.color = color;
        this.texture = texture;
        this.isPublic = isPublic;
        this.isCommentAvailable = isCommentAvailable;
        this.isGroupExhibition = isGroupExhibition;
    }
}
