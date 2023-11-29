package email;

import event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final String EVENT_ALERT_TOPIC = "event-alert";
    private static final String SUBSCRIBED_NOTIFICATION_TOPIC = "subscriber-notification";

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = SUBSCRIBED_NOTIFICATION_TOPIC, groupId = "my-group")
    public void processEvent(Event event, String email) {
        sendEmailForEvent(event, email);
    }

    private void sendEmailForEvent(Event event, String email) {
        String to = email;
        String subject = "You have subscribed to event: " + event.getName();
        String body = "This is a confirmation for the event: " + event.getName() +
                "\nEvent Date: " + event.getDate() +
                "\nEvent Description: " + event.getDescription() +
                "\nEvent Location: " + event.getLocation();

        // Send the email using the EmailService
        emailService.sendEmail(to, subject, body);

        System.out.println("Email sent for event: " + event.getName());
    }
}