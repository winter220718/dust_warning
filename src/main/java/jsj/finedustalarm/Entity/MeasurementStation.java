package jsj.finedustalarm.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "MEASUREMENT_STATION_INFO")
public class MeasurementStation {
    // 측정소 코드
    @Id
    @Column(name = "STATION_CODE")
    private String stationCode;
    // 측정소명
    @Column(name = "STATION_NAME")
    private String stationName;
}
