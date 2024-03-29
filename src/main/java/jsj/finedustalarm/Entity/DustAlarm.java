package jsj.finedustalarm.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity

@Data
@Table(name = "DUST_ALARM_HISTORY")
public class DustAlarm {
    // 날짜
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_seq")
    @SequenceGenerator(name = "alert_seq", sequenceName = "ALERT_SEQ", allocationSize = 1)
    @Column(name = "ALERT_SEQ")
    private int alertSeq;

    @Column(name = "STATION_CODE")
    private String stationCode;

    @Column(name = "INSPECT_DATE")
    private String inspectDate;
    // 등급
    @Column(name = "ALERT_GRADE")
    private int alertGrade;
}
