package com.example.backendjavaspring.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", unique = true)
    private long imageId;
    @Column(nullable = false)
    private String url;
    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public long getImageId() {
        return imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
