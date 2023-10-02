package com.hruza.carrental.http.communication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.entity.Role;
import com.hruza.carrental.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class AuthenticationResponse


{
   @JsonProperty("access_token")
   private String accessToken;

   @JsonProperty("refresh_token")
   private String refreshToken;

   @JsonProperty("user_id")
   private Long userID;

   @JsonProperty("role")
   private Role role;

}
