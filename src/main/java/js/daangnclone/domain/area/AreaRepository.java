package js.daangnclone.domain.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    List<Area> findAreaByPprAreaCd(Long pprAreaCd);
    Optional<Area> findByAreaCd(Long areaCd);

}
