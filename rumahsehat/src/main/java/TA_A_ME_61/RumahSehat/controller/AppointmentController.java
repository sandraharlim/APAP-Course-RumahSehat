package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.service.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TagihanService tagihanService;

    @GetMapping("/viewall")
    public String viewAllAppointment(Model model) { // admin dan dokter only
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

    @PostMapping("/finish/{kode}")
    private String finishAppointment(@PathVariable String kode, Model model){
        System.out.println(kode);
        AppointmentModel appointment = appointmentService.getAppointmentByKode(kode);
        ResepModel resep = appointment.getResep();
        if (resep == null || // harus di konfirmasi dulu oleh dokter (ada pop up)
                resep.getIsDone() ) { // resep udh dikonfirmasi by apoteker

            model.addAttribute("appointment", appointment);

            // Create tagihan di mana total tagihan = tarif dokter (di service)
            TagihanModel tagihan = tagihanService.addTagihanByDokter(appointment);
            appointment.setTagihan(tagihan);
            appointmentService.finishAppointment(appointment);

        } else {
            // Jika terdapat resep dari sebuah appointment dan resep tersebut belum dikonfirmasi oleh Apoteker,
            // maka Dokter tidak dapat menyelesaikan appointment.

        }
        String kembalian = "redirect:/appointment/view/" + kode;
        return kembalian;
    }
}
