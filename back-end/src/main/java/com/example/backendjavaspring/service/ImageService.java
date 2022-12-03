package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.entity.Image;

public interface ImageService {
    Image createImage(Image image);
    Image findImageById(long id);
    void deleteImage(long id);
}
