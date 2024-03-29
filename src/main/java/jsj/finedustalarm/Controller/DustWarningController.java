package jsj.finedustalarm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.tools.javac.Main;
import jsj.finedustalarm.Entity.AlarmData;
import jsj.finedustalarm.Entity.AlertGrade;
import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Service.DustWarningServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DustWarningController {

    private final DustWarningServiceImpl dustWarningService;
    boolean isAlarmed = false;

    @RequestMapping("/*")
    public void getJsonData() {

        try (BufferedReader reader = new BufferedReader(new FileReader(Main.class.getClassLoader().getResource("find_dust_seoul.json").getPath()))) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            AlarmData[] alarmData = objectMapper.readValue(reader, AlarmData[].class);

            int previousDay = LocalDateTime.parse(alarmData[0].getInspectDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH")).getDayOfYear();
            LocalDateTime inspectDate;

            for (int i = 1; i < alarmData.length; i++) {

                inspectDate = LocalDateTime.parse(alarmData[i].getInspectDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));

                if (((int) inspectDate.getDayOfYear() == previousDay)) {
                    if (isAlarmed) {
                        // 경보가 울린 경우 나머지 시간은 건너뛴다
                        continue;
                    }
                } else {
                    // 날짜가 바뀌면 리셋
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void reportWarning(int alertGrade, AlarmData alarmData) {
        DustAlarm dustAlarm = new DustAlarm();
        dustAlarm.setAlertGrade(alertGrade);
        dustAlarm.setInspectDate(alarmData.getInspectDate());
        dustAlarm.setStationCode(alarmData.getStationCode());
        // 초미세먼지경보
        isAlarmed = true;
        dustWarningService.saveDustAlarm(dustAlarm);
    }

}
