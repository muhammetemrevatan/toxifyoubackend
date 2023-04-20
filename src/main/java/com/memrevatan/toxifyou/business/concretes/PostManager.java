package com.memrevatan.toxifyou.business.concretes;

import com.memrevatan.toxifyou.business.abstracts.PostService;
import com.memrevatan.toxifyou.core.httpResponse.GenericResponse;
import com.memrevatan.toxifyou.dataAccess.PostDao;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.Post;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PostManager implements PostService {

    private final PostDao postDao;
    private final UserDao userDao;

    public PostManager(PostDao postDao, UserDao userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @Override
    public GenericResponse savePost(Post post, User user) {
        post.setUser(user);
        post.setTimestamp(new Date());
        postDao.save(post);
        return new GenericResponse("Post is saved!");
    }

    @Override
    public Page<PostDto> getPosts(Pageable page) {
//        return postDao.findAll(page).map(PostDto::new);
        return postDao.findAll(page).map((post -> new PostDto(post)));
    }

    @Override
    public Page<PostDto> getUserPosts(String username, Pageable page) {
        var user = userDao.getByUsername(username);
        return postDao.findByUser(user, page).map(PostDto::new);
    }

//    @Override
//    public ResponseEntity<?> getOldPosts(long id, Pageable page, boolean count, String direction) {
//        if(count) {
//            long newPostCount = postDao.countByIdGreaterThan(id);
//            Map<String, Long> response = new HashMap<>();
//            response.put("count", newPostCount);
//            return ResponseEntity.ok(response);
//        }
//        if(direction.equals("after")) {
//            var newPosts = postDao.findByIdGreaterThan(id, page.getSort()).stream().map(PostDto::new).toList();
//            return ResponseEntity.ok(newPosts);
//        }
//        return ResponseEntity.ok(postDao.findByIdLessThan(id, page).map(PostDto::new));
//    }

//    @Override
//    public ResponseEntity<?> getUserPostsRelative(long id, String username, Pageable page, boolean count, String direction) {
//        var user = userDao.getByUsername(username);
//        if(count) {
//            long newPostCount = postDao.countByIdGreaterThanAndUser(id, user);
//            Map<String, Long> response = new HashMap<>();
//            response.put("count", newPostCount);
//            return ResponseEntity.ok(response);
//        }
//        if(direction.equals("after")) {
//            var newPosts = postDao.findByIdGreaterThanAndUser(id, user, page.getSort()).stream().map(PostDto::new).toList();
//            return ResponseEntity.ok(newPosts);
//        }
//        return ResponseEntity.ok(postDao.findByIdLessThanAndUser(id, user, page).map(PostDto::new));
//    }

    @Override
    public ResponseEntity<?> getOldAndNewPosts(long id, String username, Pageable page, boolean count, String direction) {
        var user = userDao.getByUsername(username);
        if(username != null) {
            if(count) {
                long newPostCount = postDao.countByIdGreaterThanAndUser(id, user);
                Map<String, Long> response = new HashMap<>();
                response.put("count", newPostCount);
                return ResponseEntity.ok(response);
            }
            if(direction.equals("after")) {
                var newPosts = postDao.findByIdGreaterThanAndUser(id, user, page.getSort()).stream().map(PostDto::new).toList();
                return ResponseEntity.ok(newPosts);
            }
            return ResponseEntity.ok(postDao.findByIdLessThanAndUser(id, user, page).map(PostDto::new));
        }
        if(count) {
            long newPostCount = postDao.countByIdGreaterThan(id);
            Map<String, Long> response = new HashMap<>();
            response.put("count", newPostCount);
            return ResponseEntity.ok(response);
        }
        if(direction.equals("after")) {
            var newPosts = postDao.findByIdGreaterThan(id, page.getSort()).stream().map(PostDto::new).toList();
            return ResponseEntity.ok(newPosts);
        }
        Specification<Post> spec = idLessThan(id);
        return ResponseEntity.ok(postDao.findAll(spec, page));
//        return ResponseEntity.ok(postDao.findByIdLessThan(id, page).map(PostDto::new));
    }

    Specification<Post> idLessThan(long id) {
        return (root, query, criteriaBuilder) -> { return criteriaBuilder.lessThan(root.get("id"), id); };
    }
}
