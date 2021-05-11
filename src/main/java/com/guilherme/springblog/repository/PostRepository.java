package com.guilherme.springblog.repository;

import com.guilherme.springblog.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

}
