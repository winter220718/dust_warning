package jsj.finedustalarm.Repository;

import jsj.finedustalarm.Entity.DustAlarm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DustAlarmRepository extends CrudRepository<DustAlarm, Long> {
    @Query(value = "SELECT * " +
            "FROM DUST.DUST_ALARM_HISTORY " +
            "WHERE TO_CHAR(INSPECT_DATE, 'YYYY-MM-DD') = TO_CHAR(:localDateTime, 'YYYY-MM-DD') " +
            "AND TO_CHAR(INSPECT_DATE, 'HH24') = TO_CHAR(:localDateTime, 'HH24')", nativeQuery = true)
    List<DustAlarm> findDustAlarmByInspectDate(@Param("localDateTime") LocalDateTime localDateTime); // 날짜만 비교
}
