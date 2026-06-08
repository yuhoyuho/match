package com.realmatch.backend.user.adapter.out.persistence;

import com.realmatch.backend.user.domain.model.UserPreference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** user_preferences 테이블과 매핑되는 JPA 엔티티 */
@Getter
@Entity
@Table(name = "user_preferences")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPreferenceJpaEntity {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "preferred_gender")
  private String preferredGender;

  @Column(name = "preferred_age_min")
  private Short preferredAgeMin;

  @Column(name = "preferred_age_max")
  private Short preferredAgeMax;

  @Column(name = "preferred_region_code")
  private String preferredRegionCode;

  @Column(name = "allow_random_call", nullable = false)
  private boolean allowRandomCall;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  public static UserPreferenceJpaEntity from(UserPreference preference) {
    UserPreferenceJpaEntity entity = new UserPreferenceJpaEntity();

    entity.userId = preference.getUserId();
    entity.preferredGender = preference.getPreferredGender();
    entity.preferredAgeMin = toShort(preference.getPreferredAgeMin());
    entity.preferredAgeMax = toShort(preference.getPreferredAgeMax());
    entity.preferredRegionCode = preference.getPreferredRegionCode();
    entity.allowRandomCall =
        preference.getAllowRandomCall() == null || preference.getAllowRandomCall();
    entity.createdAt = preference.getCreatedAt();
    entity.updatedAt = preference.getUpdatedAt();

    return entity;
  }

  private static Short toShort(Integer value) {
    return value == null ? null : value.shortValue();
  }
}
