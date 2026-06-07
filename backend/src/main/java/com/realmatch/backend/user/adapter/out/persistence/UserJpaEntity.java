package com.realmatch.backend.user.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  @Column(name = "email")
  private String email;

  @Column(name = "nickname")
  private String nickname;

  public UserJpaEntity(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }

  public static UserJpaEntity createForOAuth(String email, String nickname) {
    return new UserJpaEntity(email, nickname);
  }
}
