package jsj.finedustalarm.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "INSPECTION_HISTORY")
public class InspectionHistory {
    // 날짜
    @Id
    @Column(name = "INSPECT_DATE")
    private LocalDateTime inspectDate;
    // 측정소코드
    @ManyToOne
    @JoinColumn(name = "STATION_CODE", referencedColumnName = "STATION_CODE")
    private MeasurementStation stationCode;
    // 미세먼지
    @Column(name = "FINE_DUST")
    private int findDust;
    // 초미세먼지
    @Column(name = "MICRO_DUST")
    private int microDust;
}
