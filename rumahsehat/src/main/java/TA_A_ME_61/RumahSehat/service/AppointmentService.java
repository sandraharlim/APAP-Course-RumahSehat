package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.restmodel.DokterDropdownItem;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    //    ========= method untuk fitur 6: add appt ============
    void addAppointment(AppointmentModel appointment);
    void setKodeNewAppointment(AppointmentModel appointment);
    void updateAppointment(AppointmentModel appointment);
    String validasi(AppointmentModel appointment);
    String getWaktuAwalWaktuAkhir(AppointmentModel appointment); // ex: (13.05-14.05)
    List<DokterDropdownItem> getDokterDropdownItems(List<DokterModel> listDokter);
    LocalDateTime convertWaktuAwalFromFlutter(String date, String time);

    //    ========= method untuk fitur 7: view all appt ============
    List<AppointmentModel> getAllAppt();
    List<AppointmentModel> getAllApptByDokter(DokterModel dokter);
    List<AppointmentModel> getAllApptByPasien(PasienModel pasien);
    AppointmentModel getAppointmentById(Long id);

    AppointmentModel getAppointmentByKode(String kode);

    AppointmentModel saveAppointment(AppointmentModel appointment);
}

