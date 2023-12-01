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
    @Scheduled(fixedRate = 60000)
    public void sendSubscribedEmail(Event event) {
        kafkaConsumerService.processEvent(event);
    }

    // Schedule the email sending task at 6 AM on the event date and one day before
    @Scheduled(cron = "0 0 6 * * ?")
    public void sendScheduledEmail(Event event) {
        kafkaConsumerService.processEvent(event);
    }

    // Schedule the email sending task 5 day before
    @Scheduled(cron = "0 0 0 5 * ?")
    public void sendScheduledEmailBeforeFiveDays(Event event) {
        kafkaConsumerService.processEvent(event);
    }

    // Schedule the email sending task 10 days before
    @Scheduled(cron = "0 0 0 10 * ?")
    public void sendScheduledEmailBeforeTenDays(Event event) {
        kafkaConsumerService.processEvent(event);
    }


}
