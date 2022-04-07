package com.security.authentication_server;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Otp {

    @Id
    private String username;
    private String code;

    public Otp() {
    }

    public Otp(String username, String code) {
        this.username = username;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public String getCode() {
        return code;
    }
}
