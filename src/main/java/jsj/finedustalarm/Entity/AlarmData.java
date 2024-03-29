package jsj.finedustalarm.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmData {
    @JsonProperty("날짜")
    private String inspectDate;
    @JsonProperty("측정소명")
    private String stationName;
    @JsonProperty("측정소코드")
    private String stationCode;
    @JsonProperty("PM10")
    private int pm10;
    @JsonProperty("PM2.5")
    private int pm2_5;
}
