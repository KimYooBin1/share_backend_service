package shared_backend.used_stuff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Oauth2Controller {
	@GetMapping("/")
	@ResponseBody
	public String mainAPI(){
		return "main route";
	}
}
