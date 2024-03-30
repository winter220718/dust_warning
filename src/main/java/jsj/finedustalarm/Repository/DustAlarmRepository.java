package jsj.finedustalarm.Repository;

import jsj.finedustalarm.Entity.DustAlarm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DustAlarmRepository extends CrudRepository<DustAlarm, Long> {
    @Query(value = "SELECT * FROM DUST_ALARM_HISTORY " +
            "WHERE TO_CHAR(INSPECT_DATE, 'YYYY-MM-DD') = TO_CHAR(:localDateTime, 'YYYY-MM-DD') " +
            "ORDER BY INSPECT_DATE, STATION_CODE", nativeQuery = true)
    List<DustAlarm> findDustAlarmByInspectDate(LocalDateTime localDateTime); // 날짜만 비교
}
