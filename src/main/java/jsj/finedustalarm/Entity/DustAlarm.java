package jsj.finedustalarm.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@IdClass(CompositeKeyClass.class)
@Table(name = "DUST_ALARM_HISTORY")
public class DustAlarm {

    @Id
    @Column(name = "INSPECT_DATE", nullable = false)
    // 날짜
    private LocalDateTime inspectDate;

    @Id
    @Column(name = "STATION_CODE", nullable = false)
    // 측정소 코드
    private String stationCode;

    @Column(name = "ALERT_GRADE", nullable = false)
    // 등급
    private int alertGrade;
}
