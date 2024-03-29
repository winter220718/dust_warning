package jsj.finedustalarm.Repository;

import jsj.finedustalarm.Entity.DustAlarm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DustAlarmRepository extends CrudRepository<DustAlarm, Long> {

}
