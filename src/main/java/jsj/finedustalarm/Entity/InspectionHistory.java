package jsj.finedustalarm.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "INSPECTION_HISTORY")
public class InspectionHistory {

    @Id
    @Column(name = "INSPECT_DATE")
    private LocalDateTime inspectDate;

    // 측정소코드
    @Column(name = "STATION_CODE")
    private String stationCode;

    // 미세먼지
    @Column(name = "FINE_DUST")
    private int findDust;

    // 초미세먼지
    @Column(name = "MICRO_DUST")
    private int microDust;
}
