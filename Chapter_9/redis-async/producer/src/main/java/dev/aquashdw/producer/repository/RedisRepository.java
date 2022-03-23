package dev.aquashdw.producer.repository;

import dev.aquashdw.producer.model.JobProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<JobProcess, String> {}
