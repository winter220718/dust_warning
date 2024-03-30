package jsj.finedustalarm.Service;

import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Entity.InspectionHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DustWarningService {
    void saveDustAlarm(DustAlarm dustAlarm); // 경보 내역 저장

    List<DustAlarm> searchByDate(LocalDateTime localDateTime); // 경보 내역 조회

    void saveInspection(InspectionHistory inspectionHistory); // 측정소별 점검 내역 저장
}
