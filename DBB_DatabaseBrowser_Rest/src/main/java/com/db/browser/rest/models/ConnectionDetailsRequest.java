package com.db.browser.rest.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Getter
@Setter
public class ConnectionDetailsRequest {

  @NotNull
  private String name;
  @NotBlank
  private String hostname;
  @NotBlank
  private String port;
  @NotBlank
  private String databaseName;
  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String driverClassName;

}