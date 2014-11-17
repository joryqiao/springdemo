package springdemo.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {
	
	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model){
		model.addAttribute("name", name);
		System.out.println("Hey, I will kill you.");
		return "welcome";
	}
	
	@RequestMapping("/hello")
	public ModelAndView greeting2(Model model){
		model.addAttribute("name", "Paddly");
		System.out.println("Current: " + System.currentTimeMillis());
		return new ModelAndView("/welcome");
	}
	
}
