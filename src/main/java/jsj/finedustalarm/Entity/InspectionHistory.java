package jsj.finedustalarm.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@IdClass(CompositeKeyClass.class)
@Table(name = "INSPECTION_HISTORY")
public class InspectionHistory {

    @Id
    @Column(name = "INSPECT_DATE", nullable = false)
    // 날짜
    private LocalDateTime inspectDate;

    @Id
    @Column(name = "STATION_CODE", nullable = false)
    // 측정소코드
    private String stationCode;

    // 미세먼지
    @Column(name = "FINE_DUST")
    private int findDust;

    // 초미세먼지
    @Column(name = "MICRO_DUST")
    private int microDust;
}
