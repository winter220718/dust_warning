package jsj.finedustalarm;

import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FineDustAlarmApplication {

	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(FineDustAlarmApplication.class, args);

	}

}
