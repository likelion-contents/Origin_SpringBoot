package dev.aquashdw.community.service;

import dev.aquashdw.community.boundary.model.PostDto;
import dev.aquashdw.community.boundary.model.UserDto;

import java.util.Collection;

public interface PostService {
    PostDto create(Long boardId, PostDto postDto);
    PostDto read(Long boardId, Long postId);
    Collection<PostDto> readAll(Long boardId);
    boolean update(Long boardId, Long postId, PostDto dto);
    boolean delete(Long boardId, Long postId);

}
