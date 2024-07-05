package com.solotics.storeback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    public void uploadFile(String bucketName, String folderName, String keyName, MultipartFile file) throws IOException {
        try {
            // Convertir MultipartFile a File
            File convertedFile = convertMultiPartFileToFile(file);

            // Construir la ruta completa del archivo dentro de la carpeta
            String fullKeyName = folderName + "/" + keyName;

            // Construir la solicitud para subir el objeto a S3
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullKeyName)
                    .build();

            // Subir el archivo a S3
            s3Client.putObject(request, RequestBody.fromFile(convertedFile));
        } catch (S3Exception e) {
            e.printStackTrace();
        }
    }

    // Método para convertir MultipartFile a File
    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }
}
