package com.db.browser.database.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ConnectionDetails {

    private long id;
    private String name;
    private String hostname;
    private String port;
    private String databaseName;
    private String username;
    private String password;
    private String driverClassName;
}