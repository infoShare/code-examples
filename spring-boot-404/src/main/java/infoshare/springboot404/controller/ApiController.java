package infoshare.springboot404.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("!profile")
@RequestMapping("api")
public class ApiController {

    @PostMapping("/method")
    public String executeMethod() {
        return "EXECUTED";
    }
}
