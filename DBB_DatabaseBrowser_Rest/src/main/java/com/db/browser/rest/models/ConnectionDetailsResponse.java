package com.db.browser.rest.models;

import lombok.*;

@Getter
@Builder
public class ConnectionDetailsResponse {

  private long id;
  private String name;
  private String hostname;
  private String port;
  private String databaseName;
  private String username;
  private String password;
  private String driverClassName;

}