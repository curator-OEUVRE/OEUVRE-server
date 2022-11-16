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
@Table(name = "Hashtag")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Hashtag extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    private String hashtag;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Long tagCount;

    @ColumnDefault(value = "false")
    @Column(nullable = false)
    private Boolean isHead;

    @Builder
    public Hashtag(
            Long no,
            String hashtag,
            Long tagCount,
            Boolean isHead
    ) {
        this.no = no;
        this.hashtag = hashtag;
        this.tagCount = tagCount;
        this.isHead = isHead;
    }
}
