package jsj.finedustalarm.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@IdClass(CompositeKeyClass.class)
@Table(name = "INSPECTION_HISTORY")
public class InspectionHistory {

    // 날짜
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Id
    @Column(name = "INSPECT_DATE")
    private LocalDateTime inspectDate;

    // 측정소코드
    @Id
    @Column(name = "STATION_CODE")
    private String stationCode;

    // 미세먼지
    @Column(name = "FINE_DUST")
    private int findDust;

    // 초미세먼지
    @Column(name = "MICRO_DUST")
    private int microDust;
}
