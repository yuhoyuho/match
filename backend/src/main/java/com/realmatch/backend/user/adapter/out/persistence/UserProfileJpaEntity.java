package com.realmatch.backend.user.adapter.out.persistence;

import com.realmatch.backend.user.domain.model.UserProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** user_profiles 테이블과 매핑되는 JPA 엔티티 */
@Getter
@Entity
@Table(name = "user_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileJpaEntity {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "height_cm")
  private Short heightCm;

  @Column(name = "job_title")
  private String jobTitle;

  @Column(name = "education_level")
  private String educationLevel;

  @Column(name = "mbti")
  private String mbti;

  @Column(name = "introduction")
  private String introduction;

  @Column(name = "region_code")
  private String regionCode;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  public static UserProfileJpaEntity from(UserProfile profile) {
    UserProfileJpaEntity entity = new UserProfileJpaEntity();

    entity.userId = profile.getUserId();
    entity.heightCm = toShort(profile.getHeightCm());
    entity.jobTitle = profile.getJobTitle();
    entity.educationLevel = profile.getEducationLevel();
    entity.mbti = profile.getMbti();
    entity.introduction = profile.getIntroduction();
    entity.regionCode = profile.getRegionCode();
    entity.createdAt = profile.getCreatedAt();
    entity.updatedAt = profile.getUpdatedAt();

    return entity;
  }

  private static Short toShort(Integer value) {
    return value == null ? null : value.shortValue();
  }
}
