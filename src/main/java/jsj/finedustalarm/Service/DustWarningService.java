package jsj.finedustalarm.Service;

import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Entity.InspectionHistory;

import java.util.List;

public interface DustWarningService {
    void saveDustAlarm(DustAlarm dustAlarm); // 경보 내역 저장
    void saveInspection(InspectionHistory inspectionHistory); // 측정소별 점검 내역 저장
}
