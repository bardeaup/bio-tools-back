package com.biotools.message.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
 
public class JwtResponse {
  private String token;
  private String refreshToken;
  private String type = "Bearer";
  private String username;
  private Collection<? extends GrantedAuthority> authorities;

  public JwtResponse() {
  }

  public JwtResponse(String accessToken, String refreshToken, String username, Collection<? extends GrantedAuthority> authorities) {
    this.token = accessToken;
    this.refreshToken = refreshToken;
    this.username = username;
    this.authorities = authorities;
  }
 
  public String getAccessToken() {
    return token;
  }
 
  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }
 
  public String getTokenType() {
    return type;
  }
 
  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }
 
  public String getUsername() {
    return username;
  }
 
  public void setUsername(String username) {
    this.username = username;
  }
  
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}