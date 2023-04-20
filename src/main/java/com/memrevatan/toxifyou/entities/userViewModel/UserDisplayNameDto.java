package com.memrevatan.toxifyou.entities.userViewModel;

import com.memrevatan.toxifyou.core.annotation.FileType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDisplayNameDto {

    @NotNull
    @Size(min = 4, max = 255)
    private String displayName;

    @FileType(types = {"jpeg", "png"})
    private String image;
}
