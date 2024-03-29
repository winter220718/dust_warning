package jsj.finedustalarm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.tools.javac.Main;
import jsj.finedustalarm.Entity.AlarmData;
import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Entity.InspectionHistory;
import jsj.finedustalarm.Service.DustWarningServiceImpl;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class DustWarningController {

    private final DustWarningServiceImpl dustWarningService;
    private boolean isAlarmed = false;

    public DustWarningController(DustWarningServiceImpl dustWarningService) {
        this.dustWarningService = dustWarningService;
        getJsonData();
    }

    public void getJsonData() {
        System.out.println("DustWarningController.getJsonData");
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.class.getClassLoader().getResource("Seoul_Dust_Report_March.json").getPath()))) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            AlarmData[] alarmData = objectMapper.readValue(reader, AlarmData[].class);

            int previousDay = getInspectDate(alarmData[0]).getDayOfYear();
            LocalDateTime inspectDate, sameDate;

            // 측정된 데이터가 없는 경우
            for (int i = 0; i < alarmData.length; i++) {
                inspectDate = getInspectDate(alarmData[i]);
                if (alarmData[i].getPm10() == 0 && alarmData[i].getPm2_5() == 0) {
                    for (int j = 0; j < alarmData.length; j++) {
                        sameDate = getInspectDate(alarmData[j]);
                        if (alarmData[i].getStationCode().equals(alarmData[j].getStationCode())
                                && inspectDate.getDayOfYear() == sameDate.getDayOfYear()) {
                            // 해당 일자의 점검 내역 insert
                            reportInspection(alarmData[j]);
                        }
                    }
                }
            }

            // 경보 발령 기준을 충족하는 경우
            for (int i = 1; i < alarmData.length; i++) {
                inspectDate = getInspectDate(alarmData[i]);

                if (((int) inspectDate.getDayOfYear() == previousDay)) {
                    if (isAlarmed) {
                        // 경보가 이미 울렸으면 나머지 시간은 건너뛴다
                        continue;
                    }
                } else {
                    previousDay = (int) inspectDate.getDayOfYear();
                    isAlarmed = false;
                }

                if (alarmData[i - 1].getPm2_5() >= 150 && alarmData[i].getPm2_5() >= 150) {
                    reportWarning(1, alarmData[i]);
                } else if (alarmData[i - 1].getPm10() >= 300 && alarmData[i].getPm10() >= 300) {
                    reportWarning(2, alarmData[i]);
                } else if (alarmData[i - 1].getPm2_5() >= 75 && alarmData[i].getPm2_5() >= 75) {
                    reportWarning(3, alarmData[i]);
                } else if (alarmData[i - 1].getPm10() >= 150 && alarmData[i].getPm10() >= 150) {
                    reportWarning(4, alarmData[i]);
                }
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static LocalDateTime getInspectDate(AlarmData alarmData) {
        return LocalDateTime.parse(alarmData.getInspectDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
    }

    private void reportInspection(AlarmData alarmData) {
        InspectionHistory inspectionHistory = new InspectionHistory();
        inspectionHistory.setInspectDate(getInspectDate(alarmData));
        inspectionHistory.setStationCode(alarmData.getStationCode());
        inspectionHistory.setFindDust(alarmData.getPm10()); // int형이라 null인 경우 자동으로 0 변환
        inspectionHistory.setMicroDust(alarmData.getPm2_5());
        dustWarningService.saveInspection(inspectionHistory);
    }

    private void reportWarning(int alertGrade, AlarmData alarmData) {
        DustAlarm dustAlarm = new DustAlarm();
        dustAlarm.setAlertGrade(alertGrade);
        dustAlarm.setInspectDate(getInspectDate(alarmData));
        dustAlarm.setStationCode(alarmData.getStationCode());
        isAlarmed = true;
        dustWarningService.saveDustAlarm(dustAlarm);
    }

}
