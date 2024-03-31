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
@Table(name = "DUST_ALARM_HISTORY")
public class DustAlarm {

    // 날짜
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Id
    @Column(name = "INSPECT_DATE")
    private LocalDateTime inspectDate;

    // 측정소 코드
    @Id
    @Column(name = "STATION_CODE")
    private String stationCode;

    //측정소명
    @Column(name = "STATION_NAME")
    private String stationName;

    // 등급
    @Column(name = "ALERT_GRADE", nullable = false)
    private int alertGrade;
}
