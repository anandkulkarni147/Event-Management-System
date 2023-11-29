package email;

import event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleEmailService {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Schedule the subscribed email sending task at every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void sendSubscribedEmail(Event event, String email) {
        // Send the event to Kafka
        kafkaProducerService.sendSubscribedEvent(event, email);
    }

    // Schedule the email sending task at 8 AM on the event date and one day before
    @Scheduled(cron = "0 0 6 * * ?")
    public void sendScheduledEmail(Event event, String email) {
        // Check events for today and send notification emails
        kafkaProducerService.sendEventAlert(event, email);
    }


}
