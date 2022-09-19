package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "User")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(of = "id")
public class User extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(length = 8)
    private String birthday;

    @Builder
    public User(
            Long id,
            String username,
            String birthday
    ) {
            this.id = id;
            this.username = username;
            this.birthday = birthday;
    }
}
