package event;

import chord.ChordController;
import chord.ChordNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private ChordController chordController;

    List<Event> eventList = new ArrayList<>();

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventList;
    }

    @PostMapping("/events")
    public void addEvent(@RequestBody Event event) {
        chordController.storeEventAtNode(event);
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable String eventId) {
        for (ChordNode node : chordController.getNodes().values()) {
            Event event = node.getEvent(eventId);
            if (event != null) {
                return event;
            }
        }
        return null;
    }

}
