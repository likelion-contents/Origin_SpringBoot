package dev.aquashdw.community.repository;

import dev.aquashdw.community.entity.AreaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AreaRepository extends CrudRepository<AreaEntity, Long> {
    @Query(
            "select a " +
            "from AreaEntity a " +
            "order by " +
            "  (a.latitude - :latitude) * (a.latitude - :latitude)" +
            " + (a.longitude - :longitude) * (a.longitude - :longitude)"
    )
    List<AreaEntity> findByCloseRange(Double latitude, Double longitude);

    @Query(
            nativeQuery = true,
            value =
            "select top 1 * " +
            "from area a " +
            "order by " +
            "  (a.latitude - :latitude) * (a.latitude - :latitude)" +
            " + (a.longitude - :longitude) * (a.longitude - :longitude)"
    )
    AreaEntity findTop1ByClosest(Double latitude, Double longitude);
}
