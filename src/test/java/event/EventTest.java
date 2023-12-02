package event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void getSubscribers() {
        Event event = new Event();
        String subscriberEmail = "test@gmail.com";

        event.addSubscriber(subscriberEmail);
        assertNotNull(event.getSubscribers());
    }

    @Test
    void addSubscriber() {
        Event event = new Event();
        String subscriberEmail = "test@gmail.com";

        event.addSubscriber(subscriberEmail);

        assertTrue(event.getSubscribers().contains(subscriberEmail));
    }
}
