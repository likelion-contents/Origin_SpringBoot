package dev.aquashdw.community.service;

import dev.aquashdw.community.boundary.model.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto read(Long id);
    Collection<UserDto> readAll();
    boolean update(Long id, UserDto dto);
    boolean delete(Long id);
}
