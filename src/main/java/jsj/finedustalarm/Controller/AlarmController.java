package jsj.finedustalarm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsj.finedustalarm.Entity.DustAlarm;
import jsj.finedustalarm.Service.DustWarningServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AlarmController {
    private final DustWarningServiceImpl dustWarningService;

    @Scheduled(cron = "30 23 * * * *") // 매일 밤 11시 30분
    private void checkAlarm() {
        System.out.println("실행 시간 >> " + LocalDateTime.now());
//        List<DustAlarm> result = dustWarningService.searchByDate(LocalDateTime.parse("2023-03-10 19:29:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<DustAlarm> result = dustWarningService.searchByDate(LocalDateTime.now());
        if (!result.isEmpty()) sendAlarm(result);
    }

    private static void ringAlarm(List<DustAlarm> alarm) throws URISyntaxException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAlarm = objectMapper.writeValueAsString(alarm);

        HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/dust-alarm"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonAlarm))
                .build();
    }

    @PostMapping("/dust-alarm")
    public ResponseEntity<String> sendAlarm(@RequestBody List<DustAlarm> dustAlarm) {

        System.out.println("====================================== (초)미세먼지 경보 알리미 ======================================\n");
        for (DustAlarm alarm : dustAlarm) {
            System.out.println("    ※ [" + alarm.getStationName() + "]에서 [" +
                    alarm.getInspectDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")) +
                    "]에 [" + (alarm.getAlertGrade() == 1 ? "초미세먼지 경보" : alarm.getAlertGrade() == 2 ? "미세먼지 경보" : alarm.getAlertGrade() == 3 ? "초미세먼지 주의보" : "미세먼지 주의보") + "]가 발령 되었습니다.");
            System.out.println();
        }
        System.out.println("===================================================================================================");
        return ResponseEntity.ok("알림이 성공적으로 전송됨");
    }

}
