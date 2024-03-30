package jsj.finedustalarm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.tools.javac.Main;
import jsj.finedustalarm.Entity.AlarmData;
import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Entity.InspectionHistory;
import jsj.finedustalarm.Service.DustWarningServiceImpl;
import jsj.finedustalarm.Utils.AlarmCriteria;
import org.springframework.scheduling.annotation.Scheduled;
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
        checkReport(getJsonData());
    }

    @Scheduled(fixedDelay = 5000)
    public void test() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    private void checkReport(AlarmData[] alarmData) {

        int previousDay = getDayOfYear(alarmData[0]);
        int inspectDate, compareDate;

        for (int i = 0; i < alarmData.length; i++) {
            inspectDate = getDayOfYear(alarmData[i]);

            // 경보 발령 기준을 충족하는 경우
            if (i > 0) {
                if ((inspectDate == previousDay)) {
                    if (isAlarmed) {
                        // 경보가 이미 울렸으면 나머지 시간은 건너뛴다
                        continue;
                    }
                } else {
                    previousDay = inspectDate;
                    isAlarmed = false;
                }

                if (isWarning(alarmData[i - 1], alarmData[i])) {
                    reportWarning(getWarningType(alarmData[i - 1], alarmData[i]), alarmData[i]);
                }
            }

            // 측정된 데이터가 없는 경우
            if (alarmData[i].getPm10() == 0 && alarmData[i].getPm2_5() == 0) {
                for (int j = 0; j < alarmData.length; j++) {
                    compareDate = getDayOfYear(alarmData[j]);
                    if (alarmData[i].getStationCode().equals(alarmData[j].getStationCode())
                            && inspectDate == compareDate) {
                        // 해당 일자의 점검 내역 insert
                        reportInspection(alarmData[j]);
                    }
                }
            }
        }
    }

    public AlarmData[] getJsonData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.class.getClassLoader().getResource("Seoul_Dust_Report_March.json").getPath()))) {
            ObjectMapper objectMapper = new ObjectMapper();
            AlarmData[] alarmData = objectMapper.readValue(reader, AlarmData[].class);
        return alarmData;
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isWarning(AlarmData previous, AlarmData current) {
        return (previous.getPm2_5() >= AlarmCriteria.PM2_5_WARNING_VALUE && current.getPm2_5() >= AlarmCriteria.PM2_5_WARNING_VALUE) ||
                (previous.getPm10() >= AlarmCriteria.PM10_WARNING_VALUE && current.getPm10() >= AlarmCriteria.PM10_WARNING_VALUE) ||
                (previous.getPm2_5() >= AlarmCriteria.PM2_5_ADVISORY_VALUE && current.getPm2_5() >= AlarmCriteria.PM2_5_ADVISORY_VALUE) ||
                (previous.getPm10() >= AlarmCriteria.PM10_ADVISORY_VALUE && current.getPm10() >= AlarmCriteria.PM10_ADVISORY_VALUE);
    }

    private int getWarningType(AlarmData previous, AlarmData current) {
        if (previous.getPm2_5() >= AlarmCriteria.PM2_5_WARNING_VALUE && current.getPm2_5() >= AlarmCriteria.PM2_5_WARNING_VALUE) {
            return 1;
        } else if (previous.getPm10() >= AlarmCriteria.PM10_WARNING_VALUE && current.getPm10() >= AlarmCriteria.PM10_WARNING_VALUE) {
            return 2;
        } else if (previous.getPm2_5() >= AlarmCriteria.PM2_5_ADVISORY_VALUE && current.getPm2_5() >= AlarmCriteria.PM2_5_ADVISORY_VALUE) {
            return 3;
        } else {
            // previous.getPm10() >= AlarmCriteria.PM10_ADVISORY_VALUE && current.getPm10() >= AlarmCriteria.PM10_ADVISORY_VALUE
            return 4;
        }
    }

    private static int getDayOfYear(AlarmData alarmData) {
        return getInspectDate(alarmData).getDayOfYear();
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
