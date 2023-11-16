package UI;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import event.Event;

@Controller
public class UIController {
    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("event", new Event());
        return "home";
    }

    @PostMapping("/home")
    public String createNewEvent(@ModelAttribute Event event, Model model){
        //Handle event data; store to chord
        System.out.println(event.getName());

        //Reset model attribute
        model.addAttribute("event", new Event());
        model.addAttribute("message", "Your event has been successfully posted!");
        return "home";
    }

}
