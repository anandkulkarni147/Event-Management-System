package event;

import chord.ChordController;
import chord.ChordNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Controller
public class EventController {

    @Autowired
    private ChordController chordController;

    List<Event> eventList = new ArrayList<>();

    public List<Event> getAllEvents() {
        return eventList;
    }

    public void addEvent(Event event) {
        chordController.storeEventAtNode(event);
    }

    public Event getEvent(String eventId) {
        Long nodeId = chordController.hashKey(eventId);
        TreeMap<Long, ChordNode> ring = chordController.getRing();
        ChordNode node = ring.ceilingEntry(nodeId).getValue();
        return node.getEvent(eventId);
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("event", new Event());
        return "home";
    }

    @PostMapping("/home")
    public String createNewEvent(@ModelAttribute Event event, Model model) {
        //Handle event data; store to chord
        System.out.println(event.getName());

        //Reset model attribute
        model.addAttribute("event", new Event());
        model.addAttribute("message", "Your event has been successfully posted!");
        return "home";
    }

}
