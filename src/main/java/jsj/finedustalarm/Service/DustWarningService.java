package jsj.finedustalarm.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import jsj.finedustalarm.Repository.DustWarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class DustWarningService {

    @PostConstruct
    public void init() {
        saveJson();
    }

    @Autowired
    private DustWarningRepository dustWarningRepository;

    public void saveJson() {
        System.out.println("실행?");
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.class.getClassLoader().getResource("find_dust_seoul.json").getPath()))) {
            ObjectMapper objectMapper = new ObjectMapper();
            String line;
            while ((line = reader.readLine()) != null) {
                // JSON 데이터를 객체로 변환
//                InspectionHistory inspectionHistory = objectMapper.readValue(line, InspectionHistory.class);
                // DB에 저장
//                dustWarningRepository.save(inspectionHistory);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return dustWarningRepository.save(entity);
    }
}
