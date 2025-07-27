package com.bookmyshow.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "theaters")
public class Theatre {

    @Id
    private String id;

    private String name;
    private String city;
    private String address;

    @Field("status")
    private TheatreStatus status = TheatreStatus.PENDING;

    public Theatre() {
    }

    public Theatre(String id, String name, String city, String address, TheatreStatus status) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TheatreStatus getStatus() {
        return status;
    }

    public void setStatus(TheatreStatus status) {
        this.status = status;
    }
}
