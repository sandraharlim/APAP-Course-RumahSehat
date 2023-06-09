package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AppointmentService {
    //    ========= method untuk fitur 7: view all appt ============
    List<AppointmentModel> getAllAppt();
    List<AppointmentModel> getAllApptByDokter(DokterModel dokter);
    List<AppointmentModel> getAllApptByPasien(PasienModel pasien);
    AppointmentModel getAppointmentById(Long id);

    //    ========= method untuk fitur 8: detail appt ============
    AppointmentModel getAppointmentByKode(String kode);
    void finishAppointment(AppointmentModel appointment);


    //    ========= method untuk fitur 11: detail resep ============
    void addAppointment(AppointmentModel appointment);

    // ============= chart ==================
    Map<String, Integer> getTotalApptDokters(List<DokterModel> listDokter);
    List<AppointmentModel> getAllAptAnnual(LocalDateTime first, LocalDateTime last);
}

