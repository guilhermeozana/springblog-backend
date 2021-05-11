package com.guilherme.springblog.controller;

import java.util.List;

import com.guilherme.springblog.domain.Post;
import com.guilherme.springblog.requests.PostRequest;
import com.guilherme.springblog.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/posts/")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.createPost(postRequest);
        
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> showAllPosts(){
        return new ResponseEntity<>(postService.showAllPosts(),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getSinglePost(@PathVariable Long id){
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);

    }
    
}