package dev.aquashdw.auth.client.repo;

import dev.aquashdw.auth.client.entity.OAuthClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<OAuthClientEntity, Long> {
    OAuthClientEntity findFirstByUid(String uid);
    OAuthClientEntity findFirstByClientId(String clientId);
}
