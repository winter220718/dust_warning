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

//    @Scheduled(fixedDelay = 10000)
    @Scheduled(cron = "0 5 * ? * * *") // 매시간 5분
    private void checkAlarm() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("실행 시간 >> " + LocalDateTime.now());
//        List<DustAlarm> result = dustWarningService.searchByDate(LocalDateTime.parse("2023-03-19 21:29:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<DustAlarm> result = dustWarningService.searchByDate(LocalDateTime.now());
        if (!result.isEmpty()) ringAlarm(result);
    }

    private static void ringAlarm(List<DustAlarm> alarm) throws URISyntaxException, IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAlarm = objectMapper.writeValueAsString(alarm);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9090/dust-alarm"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonAlarm))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response code >> " + response.statusCode());
        System.out.println("Response body >> " + response.body());
    }
}
