package com.realmatch.backend.user.adapter.out.persistence;

import com.realmatch.backend.user.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * users 테이블과 매핑되는 JPA 엔티티
 */
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_uuid")
  private UUID userUUID;

  @Column(name = "status")
  private String status;

  @Column(name = "email")
  private String email;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "gender")
  private String gender;

  @Column(name = "birth_year")
  private Integer birthYear;

  @Column(name = "is_profile_completed", nullable = false)
  private boolean profileCompleted;

  @Column(name = "last_login_at")
  private OffsetDateTime lastLoginAt;

  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;

  public UserJpaEntity(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }

  public static UserJpaEntity createForOAuth(String email, String nickname) {
    UserJpaEntity user = new UserJpaEntity();

    user.userUUID = UUID.randomUUID();
    user.email = email;
    user.nickname = nickname;
    user.status = "ACTIVE";
    user.profileCompleted = false;
    user.createdAt = OffsetDateTime.now();
    user.updatedAt = OffsetDateTime.now();

    return user;
  }

  public static UserJpaEntity from(User user) {
    UserJpaEntity entity = new UserJpaEntity();

    entity.userId = user.getUserId();
    entity.userUUID = user.getUserUUID();
    entity.status = user.getStatus();
    entity.email = user.getEmail();
    entity.nickname = user.getNickname();
    entity.phoneNumber = user.getPhoneNumber();
    entity.gender = user.getGender();
    entity.birthYear = user.getBirthYear();
    entity.profileCompleted = user.isProfileCompleted();
    entity.lastLoginAt = user.getLastLoginAt();
    entity.createdAt = user.getCreatedAt();
    entity.updatedAt = user.getUpdatedAt();
    entity.deletedAt = user.getDeletedAt();

    return entity;
  }
}
