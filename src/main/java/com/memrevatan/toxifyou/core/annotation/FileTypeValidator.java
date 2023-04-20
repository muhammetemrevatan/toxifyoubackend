package com.memrevatan.toxifyou.core.annotation;

import com.memrevatan.toxifyou.core.file.FileManager;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    @Autowired
    FileManager fileManager;

    String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return true;
        }
        String fileType = fileManager.detectType(value);
        for(String supportedType: this.types) {
            if(fileType.contains(supportedType)) {
                return true;
            }
        }

        String supportedTypes = String.join(", ", this.types); // types array'i icerisinde gelen degerlerin arasına virgul koyarak string ile ayırdık.
        context.disableDefaultConstraintViolation(); // default oluşturulacak olan mesajı engelleme çağrısı yapar. Yani ekrana [jpeg,png] mesajını default olusturmasını engelledik.
        var contextObject= context.unwrap(HibernateConstraintValidatorContext.class); // Yeni bir context objesi olusturduk.
        contextObject.addMessageParameter("types", supportedTypes); // bu objenin hangi key ve value karsılık gelecegini yazdık. dil dosyasında {types} kısmı için default'u [jpeg,png] olan kısmı jpeg, png olarak degistirdik. jpeg ve png yazısı dto içerinde anotaion'a parametre olarak verildi.
        contextObject.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation(); // bu degerleri default ile degistirerek set ettik.

        return false;
    }
}
