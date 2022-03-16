package dev.aquashdw.jpa.repository;

import dev.aquashdw.jpa.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
