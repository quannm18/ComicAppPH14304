package com.quannm18.comicappph14304.models;

import android.media.Image;

import java.io.Serializable;
import java.util.List;

public class Comic implements Serializable {
    private String id;
    private String name;
    private String image;
    private String author;
    private String category;
    private List<String> image_detail;

    public Comic(String id, String name, String image, String author, String category,List<String> image_detail) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.author = author;
        this.category = category;
        this.image_detail = image_detail;
    }

    public Comic() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getImage_detail() {
        return image_detail;
    }

    public void setImage_detail(List<String> image_detail) {
        this.image_detail = image_detail;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", image_detail=" + image_detail +
                '}';
    }
}
