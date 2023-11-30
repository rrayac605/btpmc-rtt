package mx.gob.imss.cit.pmc.rtt.repository;

import java.io.Serializable;
import mx.gob.imss.cit.pmc.rtt.dto.RttDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RttDetailRepository extends JpaRepository<RttDetailDTO, Serializable> {

    @Query(value = "DELETE FROM MGCARGA1.MCT_PMC_DETALLE_RTT", nativeQuery = true)
    void deleteAll();

}
