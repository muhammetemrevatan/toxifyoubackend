package com.memrevatan.toxifyou.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
// spring default davranışlarını değiştirdiğimiz veya spring konfigürasyonu ile ilgili classlar için bu anatayonu kullanıyoruz.
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    AppConfiguration appConfiguration;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // http://localhost:8080/images/profile.png
        registry.addResourceHandler("/images/**") // Eğer yukarıdaki gibi bir istek atılırsa buradaki handler devreye girer.
                .addResourceLocations("file:./"+appConfiguration.getUploadPath()+"/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Bean
    CommandLineRunner createStorageDirectories() {
        return (args) -> {
            File folder = new File(appConfiguration.getUploadPath());
            boolean folderExist = folder.exists() && folder.isDirectory();
            if(!folderExist) {
                folder.mkdir();
            }
        };
    }
}
