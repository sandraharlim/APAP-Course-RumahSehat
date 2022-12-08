package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.restmodel.AppointmentRestModel;
import TA_A_ME_61.RumahSehat.restmodel.DokterDropdownItem;
import TA_A_ME_61.RumahSehat.restmodel.ResponseNewAppointment;
import TA_A_ME_61.RumahSehat.restmodel.SubmittedAppointment;
import TA_A_ME_61.RumahSehat.restservice.AppointmentRestService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Positive;
import java.security.Principal;
import java.time.LocalDateTime;
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
    private AppointmentRestService appointmentRestService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private PasienService pasienService;

    // @Autowired
    // private AdminService adminService;

    @GetMapping("/doctors-flutter")
    public List<DokterDropdownItem> createAppointmentFlutter() {
        System.out.println("masuk get dokter");
        List<DokterModel> listDokter = dokterService.getListDokter();

        List<DokterDropdownItem> dokterDropdownItems = appointmentRestService.getDokterDropdownItems(listDokter);

        return dokterDropdownItems;
    }

    @PostMapping("/create")
    public ResponseNewAppointment createAppointmentSubmit(@RequestBody SubmittedAppointment appointment) {
        String uuidDokter = appointment.getUuid(); // "3e9ab0a6-6597-11ed-85c8-803253019798"
        String date = appointment.getDate(); // 12/21/2022
        String time = appointment.getTime(); // 12:23 AM

        AppointmentModel newAppointment = new AppointmentModel();
        ResponseNewAppointment response = new ResponseNewAppointment();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        response.setUsername(username);
        PasienModel loggedInPasien = pasienService.getPasienByUsername(username);
        if (loggedInPasien == null) {
            response.setError("Anda bukan pasien.");
            return response;
        }
        newAppointment.setPasien(loggedInPasien);

        DokterModel selectedDokter = dokterService.getDokterByUuid(uuidDokter);
        if (selectedDokter == null) {
            response.setError("Anda belum memilih dokter, atau dokter tidak ditemukan");
            return response;
        }

        newAppointment.setDokter(selectedDokter);

        // ganti format date dan time dari flutter (dari String)
        // jadi LocalDateTime
        LocalDateTime waktuAwal = appointmentRestService.convertWaktuAwalFromFlutter(date, time);
        if (waktuAwal == null) {
            response.setError("Waktu yg dipilih tidak sesuai (jam > 23 atau menit > 59)");
            return response;
        }


        newAppointment.setWaktuAwal(waktuAwal);

        // validasi jadwal dokter dan pasien
        String hasilValidasi = appointmentRestService.validasi(newAppointment);

        if (hasilValidasi.equals("Valid")) {
            appointmentRestService.addAppointment(newAppointment);
            appointmentRestService.setKodeNewAppointment(newAppointment); // udh langsung ke update di db juga

            String successMessage = "Berhasil menambahkan Appointment " +
                newAppointment.getKode() + " di jam " +
                appointmentRestService.getWaktuAwalWaktuAkhir(newAppointment);
            response.setSuccess(successMessage);
            return response;
        }

        // ada jadwal yg tabrakan
        response.setError(hasilValidasi);
        return response;
    }

    @GetMapping("/viewall")
    public List<AppointmentRestModel> viewAllAppointment() { // hanya boleh pasien
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        PasienModel pasien = pasienService.getPasienByUsername(username);

        List<AppointmentModel> listAppointment = new ArrayList<>();
        List<AppointmentRestModel> listRestAppt = new ArrayList<>();

        if (pasien != null) {
            listAppointment = appointmentService.getAllApptByPasien(pasien);
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
            listRestAppt = appointmentRestService.convertApptToRestAppt(listAppointment);
            return listRestAppt;

        } else { // role nya gabener, tp harusnya nanti udh di handle websecurityconfig sih
            return null;
        }
    }
}

