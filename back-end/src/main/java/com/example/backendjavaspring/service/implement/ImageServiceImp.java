package com.example.backendjavaspring.service.implement;


import com.example.backendjavaspring.model.entity.Image;
import com.example.backendjavaspring.repository.ImageRepository;
import com.example.backendjavaspring.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ImageServiceImp implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImp(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image createImage(Image image){
        return imageRepository.save(image);
    }

    public Image findImageById(long id){
        return imageRepository.findById(id).orElse(null);
    }

    public void deleteImage(long id){
        imageRepository.deleteById(id);
    }
}
