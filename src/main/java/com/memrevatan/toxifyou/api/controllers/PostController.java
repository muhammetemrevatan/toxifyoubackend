package com.memrevatan.toxifyou.api.controllers;

import com.memrevatan.toxifyou.business.abstracts.PostService;
import com.memrevatan.toxifyou.core.annotation.CurrentUser;
import com.memrevatan.toxifyou.core.httpResponse.GenericResponse;
import com.memrevatan.toxifyou.entities.Post;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0/")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("posts")
    GenericResponse savePost(@Valid @RequestBody Post post, @CurrentUser User user) {
        return postService.savePost(post, user);
    }

    @GetMapping("posts")
    Page<PostDto> getPosts(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return postService.getPosts(page);
    }

    @GetMapping({"posts/{id:[0-9]+}", "users/{username}/posts/{id:[0-9]+}"})
    ResponseEntity<?> getPostsRelative(@PathVariable long id,
                                       @PathVariable(required = false) String username,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
                                       @RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
                                       @RequestParam(name = "direction", defaultValue = "before") String direction) {
        return postService.getOldAndNewPosts(id ,username, page, count, direction);
    }

    @GetMapping("users/{username}/posts")
    Page<PostDto> getUserPosts(@PathVariable String username,
                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return postService.getUserPosts(username, page);
    }

//    @GetMapping("users/{username}/posts/{id:[0-9]+}")
//    ResponseEntity<?> getUserPostsRelative(@PathVariable long id,
//                                           @PathVariable String username,
//                                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
//                                           @RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
//                                           @RequestParam(name = "direction", defaultValue = "before") String direction) {
//        return postService.getUserPostsRelative(id, username, page, count, direction);
//    }

}
