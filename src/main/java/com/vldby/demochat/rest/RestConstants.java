package com.vldby.demochat.rest;

public interface RestConstants {
    interface EndPoints {
        String AUTH = "/auth";
        interface Auth {
            String LOGIN = "/login";
            String REGISTRY = "/registry";
        }

        String HELLO = "/hello";
        interface Hello {
            String USER = "/user";
            String ADMIN = "/admin";
        }
    }
}
