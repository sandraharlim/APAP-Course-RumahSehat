package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AdminModel;
import TA_A_ME_61.RumahSehat.model.ApotekerModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.security.xml.Attributes;
import TA_A_ME_61.RumahSehat.security.xml.ServiceResponse;
import TA_A_ME_61.RumahSehat.service.*;
import TA_A_ME_61.RumahSehat.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    DokterService dokterService;

    @Autowired
    ApotekerService apotekerService;

    @Autowired
    AdminService adminService;

    @Autowired
    PasienService pasienService;

    private WebClient webClient = WebClient.builder().build();

    @GetMapping("/")
    private String Home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        DokterModel dokter = dokterService.getDokterByUsername(username);
        ApotekerModel apoteker = apotekerService.getApotekerByUsername(username);
        AdminModel admin = adminService.getAdminByUsername(username);
        if (dokter != null){
            model.addAttribute("user", dokter);
        } else if (apoteker != null){
            model.addAttribute("user", apoteker);
        } else if (admin != null){
            model.addAttribute("user", admin);
        }
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
    ){
        ServiceResponse serviceResponse = this.webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET,
                        ticket,
                        Setting.CLIENT_LOGIN
                )
        ).retrieve().bodyToMono(ServiceResponse.class).block();

        Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
        String username = serviceResponse.getAuthenticationSuccess().getUser();

        AdminModel admin = adminService.getAdminByUsername(username);

        if(admin == null){
            admin = new AdminModel();
            admin.setEmail(username + "@ui.ac.id");
            admin.setNama(attributes.getNama());
            admin.setPassword("belajarbelajar");
            admin.setUsername(username);
            admin.setIsSso(true);
            admin.setRole("Admin");
            adminService.addAdmin(admin);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "belajarbelajar");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal){
        AdminModel admin = adminService.getAdminByUsername(principal.getName());
        if (admin.getIsSso() == false) {
            return new ModelAndView("redirect:/logout");
        }

        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }

    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO(){
        return new ModelAndView("redirect:"+Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

}
