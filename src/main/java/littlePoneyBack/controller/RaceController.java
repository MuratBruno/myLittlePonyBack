package littlePoneyBack.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaceController {
	  @RequestMapping("/")
	    public String index() {
	        return "Greetings from Spring Boot!";
	    }
}
