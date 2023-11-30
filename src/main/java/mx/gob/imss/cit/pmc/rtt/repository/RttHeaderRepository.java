package mx.gob.imss.cit.pmc.rtt.repository;

import java.io.Serializable;
import mx.gob.imss.cit.pmc.rtt.dto.RttHeaderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RttHeaderRepository extends JpaRepository<RttHeaderDTO, Serializable> {

    @Query(value = "DELETE FROM MGCARGA1.MCT_PMC_RTT_ENCABEZADO", nativeQuery = true)
    void deleteAll();

}
