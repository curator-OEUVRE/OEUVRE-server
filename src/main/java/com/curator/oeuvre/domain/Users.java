package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

@Getter
@Setter
@Entity
@Table(name = "Users")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Users extends AbstractTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Email
    @Column(nullable = false)
    private String email;

    @Length(min = 1, max = 15)
    @Column(nullable = false)
    private String id;

    @Length(min = 2, max = 12)
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Nullable
    private String profileImageUrl;

    @Length(min = 4, max = 15)
    @Column(nullable = false)
    private String exhibitionName;

    @Length(max = 50)
    @Nullable
    private String introduceMessage;

    @Column(length = 10)
    private String birthday;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Boolean isMessageAlarmOn;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Boolean isLikeAlarmOn;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Boolean isCommentAlarmOn;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Boolean isFollowAlarmOn;

    @ColumnDefault(value = "1")
    @Column(nullable = false)
    private Boolean isGroupRequestAlarmOn;

    @Nullable
    private String refreshToken;

    @Builder
    public Users(
            Long no,
            String email,
            String id,
            String name,
            String type,
            String profileImageUrl,
            String exhibitionName,
            String introduceMessage,
            String birthday,
            Boolean isMessageAlarmOn,
            Boolean isLikeAlarmOn,
            Boolean isCommentAlarmOn,
            Boolean isFollowAlarmOn,
            Boolean isGroupRequestAlarmOn,
            String refreshToken
    ) {
            this.no = no;
            this.email = email;
            this.id = id;
            this.name = name;
            this.type = type;
            this.profileImageUrl = profileImageUrl;
            this.exhibitionName = exhibitionName;
            this.introduceMessage = introduceMessage;
            this.birthday = birthday;
            this.isMessageAlarmOn = isMessageAlarmOn;
            this.isLikeAlarmOn = isLikeAlarmOn;
            this.isCommentAlarmOn = isCommentAlarmOn;
            this.isFollowAlarmOn = isFollowAlarmOn;
            this.isGroupRequestAlarmOn = isGroupRequestAlarmOn;
            this.refreshToken = refreshToken;
    }
}
