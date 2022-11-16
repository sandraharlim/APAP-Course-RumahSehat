package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.model.UserModel;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.DokterService;
import TA_A_ME_61.RumahSehat.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/create")
    public String createAppointmentFormPage(Model model) {
        AppointmentModel newAppointment = new AppointmentModel();
        List<DokterModel> listDokter = dokterService.getListDokter();

        model.addAttribute("appointment", newAppointment);
        model.addAttribute("listDokter", listDokter);
        return "appointment/form-create-appointment";
    }

    @PostMapping(value = "/create")
    public String createAppointmentSubmitPage(@ModelAttribute AppointmentModel appointment,
                                              RedirectAttributes redirectAttrs,
                                              Model model,
                                              Principal principal) {
        // set uuid pasien
//        PasienModel pasien = pasienService.getPasienByUsername(principal.getName());
        PasienModel pasien = pasienService.getPasienByUuid("a9ad1618-6597-11ed-85c8-803253019798");
        appointment.setPasien(pasien);

        // set ulang dokter
        DokterModel dokter = appointment.getDokter();
        if (dokter == null) {
            redirectAttrs.addFlashAttribute("error", "Anda belum memilih dokter");
            return "redirect:/appointment/create";
        }

        DokterModel dokterDariDb = dokterService.getDokterByUuid(dokter.getUuid());
        if (dokterDariDb == null) {
            redirectAttrs.addFlashAttribute("error", "Dokter tidak ditemukan");
            return "redirect:/appointment/create";
        }
        appointment.setDokter(dokterDariDb);

        String hasilValidasi = appointmentService.validasi(appointment);
        if (hasilValidasi.equals("Valid")) {
            // set isDone jd false udh di service pas nge add
            appointmentService.addAppointment(appointment);
            appointmentService.setKodeNewAppointment(appointment); // udh langsung ke update di db juga

            String successMessage = "Berhasil menambahkan Appointment " +
                    appointment.getKode() + " di jam " +
                    appointmentService.getWaktuAwalWaktuAkhir(appointment);
            redirectAttrs.addFlashAttribute("success", successMessage);
            return "redirect:/appointment"; // di redirect ke viewall
        }

        // ada jadwal yg tabrakan
        redirectAttrs.addFlashAttribute("error", hasilValidasi);
        return "redirect:/appointment/create";
    }

    @GetMapping("/{role}")
    public String viewAllAppointment(@PathVariable("role") String role,
                                     Model model, Principal principal) {
        // cek role user


        List<AppointmentModel> listAppointment = new ArrayList<>();

        if (role.equals("admin")) {
            // admin: all appt; nama dokter, pasien, waktuawal, status, tombol detail
            listAppointment = appointmentService.getAllAppt();
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
            model.addAttribute("listAppointment", listAppointment);
            return "appointment/viewall-admin-appointment";

        } else if (role.equals("dokter")) {
            // dokter: all appt milik dokter; nama pasien, waktuawal, status, tombol detail
            DokterModel dokter = dokterService.getDokterByUuid("3e9ab0a6-6597-11ed-85c8-803253019798"); // masih pake data statis dulu
            listAppointment = appointmentService.getAllApptByDokter(dokter);
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
            model.addAttribute("listAppointment", listAppointment);
            return "appointment/viewall-dokter-appointment";

        } else if (role.equals("pasien")) {
            // pasien (mobile): all appt milik pasien; nama dokter, waktu awal, status, tombol detail
            PasienModel pasien = pasienService.getPasienByUuid("a9ad1618-6597-11ed-85c8-803253019798"); // masih pake data statis dulu
            listAppointment = appointmentService.getAllApptByPasien(pasien);
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
            model.addAttribute("listAppointment", listAppointment);
            return "appointment/viewall-pasien-appointment"; // harusnya ke mobile

        } else { // error
//            return "error/400"; // bad request
            return "home";
        }
    }
}
