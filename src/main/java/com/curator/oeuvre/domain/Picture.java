package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "Picture")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Picture extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floorNo", nullable = false)
    private Floor floor;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer queue;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String manufactureYear;

    @Column(nullable = true)
    private String material;

    @Column(nullable = true)
    private String scale;

    @Nullable
    @Length(max = 100)
    private String description;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = true)
    private Float location;

    @Builder
    public Picture (
            Long no,
            Floor floor,
            String imageUrl,
            Integer queue,
            String title,
            String manufactureYear,
            String material,
            String scale,
            String description,
            Float height,
            Float width,
            Float location
    ) {
        this.no = no;
        this.floor = floor;
        this.imageUrl = imageUrl;
        this.queue = queue;
        this.title = title;
        this.manufactureYear = manufactureYear;
        this.material = material;
        this.scale = scale;
        this.description = description;
        this.height = height;
        this.width = width;
        this.location = location;
    }
}
