package dev.aquashdw.auth.repository;

import dev.aquashdw.auth.model.UserHash;
import org.springframework.data.repository.CrudRepository;

public interface RedisUserCacheRepository extends CrudRepository<UserHash, String> {}
