package event;

import chord.ChordController;
import chord.ChordNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private ChordController chordController;

    @PostMapping("/{nodeId}")
    public void addEvent(@PathVariable String nodeId, @RequestBody Event event) {
        chordController.storeEventAtNode(nodeId, event);
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
