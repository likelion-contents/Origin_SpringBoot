package dev.aquashdw.community.jpa.repository;

import dev.aquashdw.community.jpa.entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardEntity, Long> {
}
