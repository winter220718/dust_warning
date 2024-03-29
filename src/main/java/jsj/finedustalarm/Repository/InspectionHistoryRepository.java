package jsj.finedustalarm.Repository;

import jsj.finedustalarm.Entity.InspectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface InspectionHistoryRepository extends CrudRepository<InspectionHistory, Long> {
    InspectionHistory save(InspectionHistory inspectionHistory);
}
