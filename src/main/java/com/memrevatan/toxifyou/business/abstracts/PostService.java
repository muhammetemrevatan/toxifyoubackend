package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.core.httpResponse.GenericResponse;
import com.memrevatan.toxifyou.entities.Post;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {

    GenericResponse savePost(Post post, User user);

    Page<PostDto> getPosts(Pageable page);

    Page<PostDto> getUserPosts(String username, Pageable page);

//    ResponseEntity<?> getOldPosts(long id, Pageable page, boolean count, String direction);
//
//    ResponseEntity<?> getUserPostsRelative(long id, String username, Pageable page, boolean count, String direction);

    ResponseEntity<?> getOldAndNewPosts(long id, String username, Pageable page, boolean count, String direction);
}
