package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.restmodel.DokterDropdownItem;
import TA_A_ME_61.RumahSehat.restmodel.SubmittedAppointment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentRestController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private PasienService pasienService;

    @GetMapping("/doctors-flutter")
    public List<DokterDropdownItem> createAppointmentFlutter() {
        AppointmentModel newAppointment = new AppointmentModel();
        List<DokterModel> listDokter = dokterService.getListDokter();

        List<DokterDropdownItem> dokterDropdownItems = appointmentService.getDokterDropdownItems(listDokter);
        Map<String, String> doctors = new HashMap<>();

        return dokterDropdownItems;
    }

    @PostMapping("/create")
    public void createAppointmentSubmit(@RequestBody SubmittedAppointment appointment) {
        System.out.println(appointment.getUuid());
        System.out.println(appointment.getDate());
        System.out.println(appointment.getTime());
    }

}
