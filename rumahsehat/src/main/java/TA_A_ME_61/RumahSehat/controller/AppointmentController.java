package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.DokterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DokterService dokterService;

    @GetMapping("/create")
    public String createAppointmentFormPage(Model model) {
        AppointmentModel newAppointment = new AppointmentModel();
        List<DokterModel> listDokter = dokterService.getListDokter();

        model.addAttribute("appointment", newAppointment);
        model.addAttribute("listDokter", listDokter);
        return "appointment/form-create-appointment";
    }

    @PostMapping(value = "/create", params = {"save"})
    public String createAppointmentSubmitPage(@ModelAttribute AppointmentModel appointment,
                                              RedirectAttributes redirectAttrs,
                                              Model model,
                                              Principal principal) {
        // set uuid pasien


        // set uuid dokter udh dr form page
        // set isDone udh di service pas nge add
        String hasilValidasi = appointmentService.validasi(appointment);
        if (hasilValidasi.equals("Valid")) {
            appointmentService.addAppointment(appointment);
            appointmentService.setKodeNewAppointment(appointment); // udh langsung ke update di db juga

//            model.addAttribute("appointment", appointment);
//            return "appointment/success-create-appointment";

            String successMessage = "Berhasil menambahkan Appointment " +
                    appointment.getKode() + " di jam " +
                    appointmentService.getWaktuAwalWaktuAkhir(appointment);
            redirectAttrs.addFlashAttribute("success", successMessage);
            return "redirect:/appointment"; // di redirect ke viewall
        }

        redirectAttrs.addFlashAttribute("error", hasilValidasi);
        return "redirect:/appointment/create";
    }
}
