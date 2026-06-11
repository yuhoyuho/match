package com.realmatch.backend.chat.adapter.out.persistence;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatReadStatusId implements Serializable {

  private Long roomId;
  private Long userId;
}
