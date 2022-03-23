package dev.aquashdw.consumer.repository;

import dev.aquashdw.consumer.model.JobProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<JobProcess, String> {}
