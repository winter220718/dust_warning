package jsj.finedustalarm.Service;

import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Entity.InspectionHistory;

public interface DustWarningService {
    void saveDustAlarm(DustAlarm dustAlarm); // 경보 내역 저장
    void saveInspection(InspectionHistory inspectionHistory); // 측정소별 점검 내역 저장
}
