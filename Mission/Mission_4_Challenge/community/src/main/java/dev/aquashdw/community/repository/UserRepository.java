package dev.aquashdw.community.repository;

import dev.aquashdw.community.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
