package jsj.finedustalarm.Service;

import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Entity.InspectionHistory;
import jsj.finedustalarm.Repository.DustAlarmRepository;
import jsj.finedustalarm.Repository.InspectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DustWarningServiceImpl implements DustWarningService{

    private final DustAlarmRepository dustAlarmRepository;
    private final InspectionHistoryRepository inspectionHistoryRepository;

    @Override
    public void saveDustAlarm(DustAlarm dustAlarm) {
        dustAlarmRepository.save(dustAlarm);
    }

    @Override
    public void saveInspection(InspectionHistory inspectionHistory) {
        inspectionHistoryRepository.save(inspectionHistory);
    }
}
