package com.memrevatan.toxifyou.entities.userViewModel;

import com.memrevatan.toxifyou.entities.Post;
import lombok.Data;

@Data

public class PostDto {

    private long id;

    private String content;

    private long timestamp;

    private UserDto user;

    public PostDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.timestamp = post.getTimestamp().getTime();
        this.user = new UserDto(post.getUser());
    }
}
