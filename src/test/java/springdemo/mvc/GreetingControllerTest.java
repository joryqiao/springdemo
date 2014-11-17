package springdemo.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;

public class GreetingControllerTest {

	@Test
	public void greetingTest() throws Exception{
		standaloneSetup(new GreetingController()).build()
		.perform(get("/greeting"))
		.andExpect(status().isOk())
		//.andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
		.andExpect(content().string("welcome"))
		;
		
	}
	
}
