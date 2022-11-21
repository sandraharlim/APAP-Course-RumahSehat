package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.restmodel.DokterDropdownItem;
import TA_A_ME_61.RumahSehat.restmodel.ResponseNewAppointment;
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
    private DokterService dokterService;

    @Autowired
    private PasienService pasienService;

    @GetMapping("/doctors-flutter")
    public List<DokterDropdownItem> createAppointmentFlutter() {
        System.out.println("masuk get dokter");
        List<DokterModel> listDokter = dokterService.getListDokter();

        List<DokterDropdownItem> dokterDropdownItems = appointmentService.getDokterDropdownItems(listDokter);

        return dokterDropdownItems;
    }

    @PostMapping("/create")
    public ResponseNewAppointment createAppointmentSubmit(@RequestBody SubmittedAppointment appointment) {
        String uuidDokter = appointment.getUuid();
        String date = appointment.getDate();
        String time = appointment.getTime();

        System.out.println("masuk submit");

        // System.out.println(uuidDokter); // "3e9ab0a6-6597-11ed-85c8-803253019798"
        // System.out.println(date); // 12/21/2022
        // System.out.println(time); // 12:23 AM

        AppointmentModel newAppointment = new AppointmentModel();
        ResponseNewAppointment response = new ResponseNewAppointment();

        // pasiennya masih statis, nanti bisa ambil dari token login
        PasienModel pasien = pasienService.getPasienByUuid("a9ad1618-6597-11ed-85c8-803253019798");
        newAppointment.setPasien(pasien);

        DokterModel selectedDokter = dokterService.getDokterByUuid(uuidDokter);
        if (selectedDokter == null) {
            response.setError("Anda belum memilih dokter, atau dokter tidak ditemukan");
            return response;
        } 

        newAppointment.setDokter(selectedDokter);

        // ganti format date dan time dari flutter (dari String) 
        // jadi LocalDateTime
        LocalDateTime waktuAwal = appointmentService.convertWaktuAwalFromFlutter(date, time);
        newAppointment.setWaktuAwal(waktuAwal);

        // validasi jadwal dokter dan pasien
        String hasilValidasi = appointmentService.validasi(newAppointment);
        if (hasilValidasi.equals("Valid")) {
            appointmentService.addAppointment(newAppointment);
            appointmentService.setKodeNewAppointment(newAppointment); // udh langsung ke update di db juga

            String successMessage = "Berhasil menambahkan Appointment " +
                newAppointment.getKode() + " di jam " +
                appointmentService.getWaktuAwalWaktuAkhir(newAppointment);
            
            response.setSuccess(successMessage);
            return response;
        }

        // ada jadwal yg tabrakan
        response.setError(hasilValidasi);
        return response;
    }

}
