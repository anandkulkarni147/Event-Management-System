package email;

import event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String EVENT_ALERT_TOPIC = "event-alert";
    private static final String SUBSCRIBED_NOTIFICATION_TOPIC = "subscriber-notification";

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public void sendEventAlert(Event event, String email) {
        kafkaTemplate.send(EVENT_ALERT_TOPIC, event);
    }
    public void sendSubscribedEvent(Event event, String email) {
        kafkaTemplate.send(SUBSCRIBED_NOTIFICATION_TOPIC, event);
    }
}
