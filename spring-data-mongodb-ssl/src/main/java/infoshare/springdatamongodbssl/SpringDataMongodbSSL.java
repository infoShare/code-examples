package infoshare.springdatamongodbssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class SpringDataMongodbSSL {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataMongodbSSL.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void close(ApplicationReadyEvent event) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Closing context");
                SpringApplication.exit(event.getApplicationContext(), () -> 0);
                System.out.println("Context closed");
            }
        }, 15 * 1000);
    }
}
