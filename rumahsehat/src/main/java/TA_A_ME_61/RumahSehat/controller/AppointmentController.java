package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.service.AdminService;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.DokterService;
import TA_A_ME_61.RumahSehat.service.PasienService;
import TA_A_ME_61.RumahSehat.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AdminService adminService;

    @GetMapping("/{role}")
    public String viewAllAppointment(@PathVariable("role") String role,
                                     Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        DokterModel dokter = dokterService.getDokterByUsername(username);
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

        } else { // role nya gabener, tp harusnya nanti udh di handle websecurityconfig sih
            model.addAttribute("errorMessage", "Anda (" + username + ") tidak memiliki akses untuk membuka halaman ini (role salah).");
            return "error/400";
        }
    }

    @GetMapping("/view/{kode}")
    private String getDetailAppointment(@PathVariable String kode, Model model){
        AppointmentModel appointment = appointmentService.getAppointmentByKode(kode);
        model.addAttribute("appointment", appointment);
        return "appointment/detail-appointment";
    }

    @PostMapping("/finish")
    private String finishAppointment(@RequestParam("kode") String kode, Model model){
        AppointmentModel appointment = appointmentService.getAppointmentByKode(kode);
        if (appointment.getResep() == null){
            appointment.setIsDone(true);
            appointmentService.saveAppointment(appointment);
            model.addAttribute("appointment", appointment);
            // Create tagihan di mana total tagihan = tarif dokter

            // Jika terdapat resep dari sebuah appointment dan resep tersebut belum dikonfirmasi oleh Apoteker,
            // maka Dokter tidak dapat menyelesaikan appointment.

        }
        String kembalian = "redirect:/appointment/view/" + kode;
        return kembalian;
    }
}
