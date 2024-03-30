package jsj.finedustalarm.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * 복합키 설정
 * */
public class CompositeKeyClass implements Serializable {
    private String stationCode;
    private LocalDateTime inspectDate;
}
