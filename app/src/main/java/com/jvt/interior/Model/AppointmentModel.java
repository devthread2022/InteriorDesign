package com.jvt.interior.Model;

public class AppointmentModel {
    String name, email, mobile, state, city, whatsApp, id;

    public AppointmentModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppointmentModel(String name, String email, String mobile, String state, String city, String whatsApp, String id) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.state = state;
        this.city = city;
        this.whatsApp = whatsApp;
        this.id = id;
    }
}
