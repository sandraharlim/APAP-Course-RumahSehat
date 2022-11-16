package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.UserModel;
import TA_A_ME_61.RumahSehat.security.xml.Attributes;
import TA_A_ME_61.RumahSehat.security.xml.ServiceResponse;
import TA_A_ME_61.RumahSehat.service.UserService;
import TA_A_ME_61.RumahSehat.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class BaseController {
    @Autowired
    UserService userService;

    private WebClient webClient = WebClient.builder().build();
    @GetMapping("/")
    private String Home(){
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest request
    ) {
        ServiceResponse serviceResponse = this.webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET,
                        ticket,
                        Setting.CLIENT_LOGIN
                )
        ).retrieve().bodyToMono(ServiceResponse.class).block();

        Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
        String username = serviceResponse.getAuthenticationSuccess().getUser();

        UserModel user = userService.getUserByUsername(username);

        if (user == null) {
            user = new UserModel();
            user.setEmail(username + "@ui.ac.id");
            user.setNama(attributes.getNama());
            user.setPassword("belajarbelajar");
            user.setUsername(username);
//            user.setIsSso(true);
            user.setRole("Admin");
            userService.addUser(user);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "belajarbelajar");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return new ModelAndView("redirect:/");
    }
    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO() { // WHAT IS MODELANDVIEW
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
        // redirect ini maksutnya apaaaa
    }

    @GetMapping(value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        if (!user.getRole().equals("Admin")) { // user login nya pake data dr db. Karna kl pake sso pasti jd admin
            return new ModelAndView("redirect:/logout");
        } // user loginnya pake sso ui
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }
}
