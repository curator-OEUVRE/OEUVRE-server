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

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    @ColumnDefault("FULL")
    private String gradient;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer texture;

    @Column(nullable = false)
    @ColumnDefault("'CENTER'")
    private String alignment;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isFramed;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isPublic;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isCommentAvailable;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isGroupExhibition;

    @Builder
    public Floor(
            Long no,
            User user,
            Integer queue,
            String name,
            String description,
            String color,
            String gradient,
            Integer texture,
            String alignment,
            Boolean isFramed,
            Boolean isPublic,
            Boolean isCommentAvailable,
            Boolean isGroupExhibition
    ) {
        this.no = no;
        this.user = user;
        this.queue = queue;
        this.name = name;
        this.description = description;
        this.color = color;
        this.gradient = gradient;
        this.texture = texture;
        this.alignment = alignment;
        this.isFramed = isFramed;
        this.isPublic = isPublic;
        this.isCommentAvailable = isCommentAvailable;
        this.isGroupExhibition = isGroupExhibition;
    }
}
