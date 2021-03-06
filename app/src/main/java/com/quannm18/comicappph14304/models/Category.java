package com.quannm18.comicappph14304.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String name;
    private String image;
    private String idCode;

    public Category(String id, String name, String image, String idCode) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.idCode = idCode;
    }

    public Category() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }
}
