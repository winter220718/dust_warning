package jsj.finedustalarm.Service;

import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Entity.InspectionHistory;
import jsj.finedustalarm.Repository.DustAlarmRepository;
import jsj.finedustalarm.Repository.InspectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DustWarningServiceImpl implements DustWarningService{

    private final DustAlarmRepository dustAlarmRepository;
    private final InspectionHistoryRepository inspectionHistoryRepository;

    @Override
    public void saveDustAlarm(DustAlarm dustAlarm) {
        dustAlarmRepository.save(dustAlarm);
        System.out.println("DustWarningServiceImpl.saveDustAlarm");
        System.out.println("dustAlarm = " + dustAlarm);
    }

    @Override
    public void saveInspection(InspectionHistory inspectionHistory) {
        inspectionHistoryRepository.save(inspectionHistory);
    }
}
