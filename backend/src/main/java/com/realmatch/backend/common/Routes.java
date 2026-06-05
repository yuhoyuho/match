package com.realmatch.backend.common;

/** API 경로 상수 클래스 REALMATCH+의 모든 API 엔드포인트를 중앙에서 관리합니다. */
public class Routes {

  // TODO: 실제 API 명세가 확정되면 컨트롤러에서 문자열을 직접 쓰지 않고 이 상수를 사용하도록 정리합니다.
  public static final String API_BASE = "/api/v1";

  public static final String AUTH_BASE = API_BASE + "/auth";
  public static final String AUTH_SOCIAL_LOGIN = AUTH_BASE + "/social/login";
  public static final String AUTH_REFRESH = AUTH_BASE + "/token/refresh";
  public static final String AUTH_LOGOUT = AUTH_BASE + "/logout";
  public static final String AUTH_PROVIDER_LINK = AUTH_BASE + "/providers/link";

  public static final String USERS_BASE = API_BASE + "/users";
  public static final String USERS_ME = USERS_BASE + "/me";
  public static final String USERS_ME_PROFILE = USERS_ME + "/profile";
  public static final String USERS_ME_PREFERENCES = USERS_ME + "/preferences";
  public static final String USERS_BLOCK_BY_ID = USERS_BASE + "/{targetUserId}/block";

  public static final String RECOMMENDATIONS_BASE = API_BASE + "/recommendations";
  public static final String RECOMMENDATIONS_LIKE = RECOMMENDATIONS_BASE + "/{targetUserId}/like";
  public static final String RECOMMENDATIONS_PASS = RECOMMENDATIONS_BASE + "/{targetUserId}/pass";
  public static final String MATCHES_BASE = API_BASE + "/matches";
  public static final String MATCHES_BY_ID = MATCHES_BASE + "/{matchId}";

  public static final String CHAT_ROOMS_BASE = API_BASE + "/chat/rooms";
  public static final String CHAT_ROOMS_BY_ID = CHAT_ROOMS_BASE + "/{roomId}";
  public static final String CHAT_MESSAGES = CHAT_ROOMS_BY_ID + "/messages";
  public static final String CHAT_READ = CHAT_ROOMS_BY_ID + "/read";
  public static final String CHAT_CLOSE = CHAT_ROOMS_BY_ID + "/close";

  public static final String RANDOM_CALL_BASE = API_BASE + "/calls/random";
  public static final String RANDOM_CALL_ENTER = RANDOM_CALL_BASE + "/enter";
  public static final String RANDOM_CALL_CANCEL = RANDOM_CALL_BASE + "/cancel";
  public static final String RANDOM_CALL_STATUS = RANDOM_CALL_BASE + "/status";
  public static final String CALLS_BY_ID = API_BASE + "/calls/{callId}";
  public static final String CALLS_END = CALLS_BY_ID + "/end";

  public static final String PAYMENTS_BASE = API_BASE + "/payments";
  public static final String PAYMENT_PRODUCTS = PAYMENTS_BASE + "/products";
  public static final String PAYMENT_VERIFY = PAYMENTS_BASE + "/verify";
  public static final String PAYMENT_REFUNDS = PAYMENTS_BASE + "/refunds";
  public static final String COINS_WALLET = API_BASE + "/coins/wallet";
  public static final String COINS_LEDGER = API_BASE + "/coins/ledger";
  public static final String COINS_USE = API_BASE + "/coins/use";

  public static final String COMMUNITY_POSTS = API_BASE + "/community/posts";
  public static final String COMMUNITY_POSTS_BY_ID = COMMUNITY_POSTS + "/{postId}";
  public static final String COMMUNITY_COMMENTS = COMMUNITY_POSTS_BY_ID + "/comments";
  public static final String COMMUNITY_LIKE = COMMUNITY_POSTS_BY_ID + "/like";
  public static final String COMMUNITY_REPORTS = API_BASE + "/community/reports";

  public static final String ADMIN_BASE = API_BASE + "/admin";
  public static final String ADMIN_USERS = ADMIN_BASE + "/users";
  public static final String ADMIN_USER_SANCTIONS = ADMIN_USERS + "/{userId}/sanctions";
  public static final String ADMIN_MODERATION_CASES = ADMIN_BASE + "/moderation/cases";
  public static final String ADMIN_MODERATION_RESOLVE =
      ADMIN_MODERATION_CASES + "/{caseId}/resolve";
  public static final String ADMIN_ACTION_LOGS = ADMIN_BASE + "/action-logs";
  public static final String ADMIN_DASHBOARD_METRICS = ADMIN_BASE + "/dashboard/metrics";
}
