package com.example.p3l.Volley.api;

public class DriverApi {
    public static final String BASE_URL = CustomerApi.BASE_URL;

    public static final String GET_ALL_URL = BASE_URL + "driver";
    public static final String GET_BY_ID_URL = BASE_URL + "driver/";
    public static final String UPDATE_STATUS_URL = BASE_URL + "driver/status/";
    public static final String UPDATE_URL = BASE_URL + "driver/mobile/";
    public static final String DELETE_URL = BASE_URL + "driver/";
    public static final String LOGIN_URL = BASE_URL + "logindriver/";
    public static final String LOGOUT_URL = BASE_URL + "logoutdriver/";
}
