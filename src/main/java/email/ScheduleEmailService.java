package email;

import event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEmailService {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    // Schedule the subscribed email sending task at every 60 seconds
    @Scheduled(fixedRate = 20000)
    public void sendSubscribedEmail(Event event) {
        System.out.println("consumer called");
        kafkaConsumerService.processEvent(event);
    }

    // Schedule the email sending task at 8 AM on the event date and one day before
    @Scheduled(cron = "0 0 6 * * ?")
    public void sendScheduledEmail(Event event) {
        // Check events for today and send notification emails
        kafkaConsumerService.processEvent(event);
    }


}
