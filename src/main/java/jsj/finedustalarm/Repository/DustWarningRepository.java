package jsj.finedustalarm.Repository;

import jsj.finedustalarm.Entity.InspectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DustWarningRepository extends JpaRepository<InspectionHistory, Long> {

}
