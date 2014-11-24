package springdemo.restful;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRestfulController {

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/RestGreeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name ){
	System.out.println("rest working..." + counter.get());
	return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    
}
