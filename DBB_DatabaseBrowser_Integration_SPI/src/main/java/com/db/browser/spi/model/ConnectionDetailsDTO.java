package com.db.browser.spi.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConnectionDetailsDTO {

  private long id;
  private String name;
  private String hostname;
  private String port;
  private String databaseName;
  private String username;
  private String password;
  private String driverClassName;


}