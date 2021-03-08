package com.giczi.david.flight.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.service.FlightService;
import org.slf4j.Logger;



@Controller
@RequestMapping("/flight")
public class FlightController {

	private FlightService service;
	private final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	public void setService(FlightService service) {
		this.service = service;
	}
	
	@RequestMapping("/orders")
	public String showAllPassengers(Model model) {
		
		model.addAttribute("passengers", service.getAllData());
			
		return "orders";
	}
	

	@RequestMapping("/login")
	public String goLoginPage() {
		
		System.out.println(service.findById(3l).getPassword());
		
		return "auth/login";
	}
	

	@RequestMapping("/registration")
	public String goRegistrationPage(Model model) {
		
		model.addAttribute("user", new Passenger());
		
		return "regist";
	}
	
	@PostMapping("/reg")
	public String greetingSubmit(@ModelAttribute Passenger user) {
		service.savePassenger(user);
		return "auth/login";
	}
	
}
