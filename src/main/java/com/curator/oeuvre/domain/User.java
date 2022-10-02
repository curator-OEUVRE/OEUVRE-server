package com.curator.oeuvre.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Collections;

@ToString
@Getter
@Setter
@Entity
@Table(name = "User")
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class User extends AbstractTimestamp implements UserDetails {

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
    public User(
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
