package jsj.finedustalarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@EnableScheduling
@SpringBootApplication
public class FineDustAlarmApplication {
    public static void main(String[] args) {
        SpringApplication.run(FineDustAlarmApplication.class, args);
    }
}
