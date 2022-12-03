package com.example.backendjavaspring.service.implement;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.example.backendjavaspring.model.Constants.URLDOWLOADIMAGE;

@Service
public class UpLoadFileService {
    private Storage storage;


    @EventListener
    public void init(ApplicationReadyEvent event) throws IOException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./demojavaspring-931ff8c70d7c.json");

        assert serviceAccount != null;
        storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("demojavaspring.appspot.com")
                .build()
                .getService();

    }


    public String upLoadFile(MultipartFile file) throws IOException {
        String fileName = generateFileName(file.getOriginalFilename());
        Bucket bucket = StorageClient.getInstance().bucket();

        Map<String, String> map = new HashMap<>();
        map.put("StorageDoAnTotNghiep", fileName);

        BlobId blobId = BlobId.of(bucket.getName(), fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        return URLDOWLOADIMAGE + fileName;
    }

    public List<String> upLoadFile(MultipartFile[] files) throws IOException {
        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = generateFileName(file.getOriginalFilename());
            Bucket bucket = StorageClient.getInstance().bucket();

            Map<String, String> map = new HashMap<>();
            map.put("StorageDoAnTotNghiep", fileName);

            BlobId blobId = BlobId.of(bucket.getName(), fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setMetadata(map)
                    .setContentType(file.getContentType())
                    .build();

            storage.create(blobInfo, file.getBytes());
            fileNames.add(URLDOWLOADIMAGE + fileName);
        }

        return fileNames;
    }

    public void deleteFile(String fileName){
        Bucket bucket = StorageClient.getInstance().bucket();
        BlobId blobId = BlobId.of(bucket.getName(), fileName);
        System.out.println(storage.delete(blobId));
    }


    private String generateFileName(String originalFileName) {
        return UUID.randomUUID() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}
