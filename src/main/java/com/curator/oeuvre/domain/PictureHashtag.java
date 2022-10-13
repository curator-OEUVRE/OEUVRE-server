package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "PictureHashtag")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class PictureHashtag extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_no", nullable = false)
    private Picture picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_no", nullable = false)
    private Hashtag hashtag;

    @Builder
    public PictureHashtag (
            Long no,
            Picture picture,
            Hashtag hashtag
    ) {
        this.no = no;
        this.picture = picture;
        this.hashtag = hashtag;
    }
}
