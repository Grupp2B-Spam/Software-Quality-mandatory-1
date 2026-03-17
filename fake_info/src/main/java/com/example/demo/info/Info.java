package com.example.demo.info;
public class Info {

    public static final String HOST = "localhost";
    public static final String DB_NAME = "addresses";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    public static String host() {
        String value = System.getenv("DB_HOST");
        return (value != null) ? value : HOST;
    }

    public static String dbName() {
        String value = System.getenv("DB_NAME");
        return (value != null) ? value : DB_NAME;
    }

    public static String user() {
        String value = System.getenv("DB_USER");
        return (value != null) ? value : USER;
    }

    public static String password() {
        String value = System.getenv("DB_PASSWORD");
        if (value == null) {
            return PASSWORD;
        }
        return value;
    }
}