package dev.aquashdw.community.jpa.repository;

import dev.aquashdw.community.jpa.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {

}
