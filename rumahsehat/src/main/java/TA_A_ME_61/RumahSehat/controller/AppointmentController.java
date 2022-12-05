package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AdminModel;
import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.model.UserModel;
import TA_A_ME_61.RumahSehat.service.AdminService;
import TA_A_ME_61.RumahSehat.service.ApotekerService;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.DokterService;
import TA_A_ME_61.RumahSehat.service.PasienService;
import TA_A_ME_61.RumahSehat.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private PasienService pasienService;

    @Autowired
    private AdminService adminService;


    @GetMapping("/viewall")
    public String viewAllAppointment(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        DokterModel dokter = dokterService.getDokterByUsername(username);
        PasienModel pasien = pasienService.getPasienByUsername(username);
        AdminModel admin = adminService.getAdminByUsername(username);

        List<AppointmentModel> listAppointment = new ArrayList<>();

        if (admin != null) {
            listAppointment = appointmentService.getAllAppt();
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
            model.addAttribute("listAppointment", listAppointment);
            return "appointment/viewall-admin-appointment";

        } else if (dokter != null) {
            listAppointment = appointmentService.getAllApptByDokter(dokter);
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
            model.addAttribute("listAppointment", listAppointment);
            return "appointment/viewall-dokter-appointment";

        } else if (pasien != null) {
            listAppointment = appointmentService.getAllApptByPasien(pasien);
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
            model.addAttribute("listAppointment", listAppointment);
            return "appointment/viewall-pasien-appointment"; // harusnya ke mobile

        } else { // role nya gabener, tp harusnya nanti udh di handle websecurityconfig sih
            return "error/400";
        }
    }
}
