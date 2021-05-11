package com.guilherme.springblog.service;

import java.time.Instant;
import java.util.List;

import com.guilherme.springblog.domain.Post;
import com.guilherme.springblog.exception.PostNotFoundException;
import com.guilherme.springblog.repository.PostRepository;
import com.guilherme.springblog.requests.PostRequest;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final AuthService authService;
    private final PostRepository postRepository;

    public void createPost(PostRequest postRequest){

        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user logged in"));

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .username(loggedInUser.getUsername())
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
                postRepository.save(post);
    }

    public List<Post> showAllPosts() {
        return postRepository.findAll();
    }

    public Post readSinglePost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("For id: "+id));
    }
}
