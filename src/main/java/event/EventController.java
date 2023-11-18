package event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import chord.ChordController;
import chord.ChordNode;

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

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/")
    public String login(@RequestParam("email") String email, HttpSession session){
        System.out.println("\n*************************************New Login*************************************\n"+email);
        session.setAttribute("email", email);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        Event techSummit = new Event("Tech Summit 2023", new Date(), "A convergence of industry experts and innovators discussing the future of technology.", "Grand Convention Center");

        Event musicFestival = new Event("Harmony Fest 2023", new Date(), "A three-day music extravaganza featuring artists from various genres.", "Green Meadows Amphitheater");

        Event writingWorkshop = new Event("Creative Writing Workshop", new Date(), "A hands-on workshop focusing on developing storytelling skills.", "City Library Auditorium");

        eventList.add(techSummit);
        eventList.add(musicFestival);
        eventList.add(writingWorkshop);

        String message = (String) session.getAttribute("message");
        if(message!=null){
            model.addAttribute("message", message);
        }
        
        String subscriptionMessage = (String) session.getAttribute("subscriptionMessage");
        if(subscriptionMessage!=null){
            model.addAttribute("subscriptionMessage", subscriptionMessage);
        }

        model.addAttribute("eventList", eventList);
        model.addAttribute("event", new Event());
        return "home";
    }

    @PostMapping("/createNewEvent")
    public String createNewEvent(@ModelAttribute Event event, HttpSession session, Model model) {
        //Handle event data; store to chord
        String email = (String) session.getAttribute("email");
        System.out.println(email+" created a new event - "+event.getName());

        session.setAttribute("message", "Your event has been successfully posted!");

        return "redirect:/home";
    }

    @PostMapping("/subscribe")
    public String subscribeToEvent(@RequestParam("eventName") String eventName, HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        System.out.println(email+" subscribed to event - "+eventName);
        session.setAttribute("subscriptionMessage", "You are now subscribed to a new event - "+eventName);
        return "redirect:/home";
    }

}
