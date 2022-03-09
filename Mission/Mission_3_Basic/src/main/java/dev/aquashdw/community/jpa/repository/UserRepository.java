package dev.aquashdw.community.jpa.repository;

import dev.aquashdw.community.jpa.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
