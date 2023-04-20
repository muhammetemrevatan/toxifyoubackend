package com.memrevatan.toxifyou.core.file;

import com.memrevatan.toxifyou.business.concretes.UserManager;
import com.memrevatan.toxifyou.core.configuration.AppConfiguration;
import com.memrevatan.toxifyou.core.httpResponse.error.ApiError;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileManager {

    AppConfiguration appConfiguration;
    Tika tika;

    public FileManager(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
    }

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);
    public String writeBase64EncodedStringToFile(String image) throws IOException {

        String fileName = generateRandomName();
        File file = new File(appConfiguration.getUploadPath() + "/" +fileName);
        OutputStream outputStream = new FileOutputStream(file);

        byte[] base64Encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64Encoded);
        outputStream.close();
        return fileName;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void deleteFile(String oldImageName) {
        if (oldImageName == null) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(), oldImageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String detectType(String value) {
        byte[] base64Encoded = Base64.getDecoder().decode(value);
        return tika.detect(base64Encoded);
    }
}
