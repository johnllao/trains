package trains.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String about(Model model) {
		model.addAttribute("title", "Train Routes");
		return "index";
	}
}
