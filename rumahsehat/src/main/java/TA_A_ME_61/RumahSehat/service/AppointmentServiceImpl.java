package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    AppointmentDb appointmentDb;

    @Autowired
    DokterService dokterService;

    @Override
    public void addAppointment(AppointmentModel appointment) {
        appointment.setIsDone(Boolean.FALSE);
        appointmentDb.save(appointment);
    }

    @Override
    public void setKodeNewAppointment(AppointmentModel appointment) {
        // dipanggil oleh controller setelah dr controller manggil addappointment
        Long id = appointment.getId();
        String kode = "APT-" + id;
        appointment.setKode(kode);

        // override data sebelumnya agar terupdate kode nya yg semula masih null.
        updateAppointment(appointment);
    }

    @Override
    public void updateAppointment(AppointmentModel appointment) {
        appointmentDb.save(appointment);
    }

    @Override
    public String validasi(AppointmentModel appointment) {

        String uuidDokter = appointment.getDokter().getUuid();
        if (dokterService.getDokterByUuid(uuidDokter) == null) {
            return "Dokter tidak ditemukan";
        }

        // Kalau error akan ditampilkan pesan:
        // Appointment A (13.05-14.05), akan dibuat Appointment B dari jam (13.45-14.45)
        LocalDateTime waktuAwalNewAppt = appointment.getWaktuAwal();
        LocalDateTime waktuAkhirNewAppt = waktuAwalNewAppt.plusHours(1); // https://www.geeksforgeeks.org/localdatetime-plushours-method-in-java-with-examples/

        List<AppointmentModel> listApptSameDokter = appointmentDb.findAllByDokter(uuidDokter);

//        int year = waktuAwalNewAppt.getYear();
//        int dayOfYear = waktuAwalNewAppt.getDayOfYear();
//        List<AppointmentModel> listApptSameDokterAndDate = appointmentDb.findAllByDokterAndDate(uuidDokter, year, dayOfYear);

        for (AppointmentModel appt : listApptSameDokter) {
            // "old" disini maksutnya appt yg udh terdaftar di DB,
            // bukan berarti waktu janjian pasti sebelum si NewAppt
            LocalDateTime waktuAwalOldAppt = appt.getWaktuAwal();
            LocalDateTime waktuAkhirOldAppt = appt.getWaktuAwal().plusHours(1);

            Boolean tabrakanPrevAppt = waktuAwalNewAppt.isBefore(waktuAkhirOldAppt); // tabrakan dengan appt sebelumnya
            Boolean tabrakanNextAppt = waktuAkhirNewAppt.isAfter(waktuAwalOldAppt); // tabrakan dengan appt setelahnya

            if (tabrakanPrevAppt || tabrakanNextAppt) { // ga valid
                String rangeWaktuOldAppt = getWaktuAwalWaktuAkhir(appt);
                String rangeWaktuNewAppt = getWaktuAwalWaktuAkhir(appointment);
                return "Appointment baru di jam " + rangeWaktuNewAppt +
                        " memiliki konflik jadwal dengan Appointment " +
                        appt.getKode() + " di jam " + rangeWaktuOldAppt + ".";
            }
        }
        // valid
        return "Valid";
    }

    @Override
    public String getWaktuAwalWaktuAkhir(AppointmentModel appointment) {
        LocalDateTime waktuAwal = appointment.getWaktuAwal();
        LocalDateTime waktuAkhir = waktuAwal.plusHours(1);

        // https://www.geeksforgeeks.org/localdatetime-gethour-method-in-java-with-examples/
        int jamAwal = waktuAwal.getHour(); // 0 - 23
        int jamAkhir = waktuAkhir.getHour();

        String jamAwalStr = (jamAwal < 10) ? ("0"+jamAwal) : Integer.toString(jamAwal);
        String jamAkhirStr = (jamAkhir < 10) ? ("0"+jamAkhir) : Integer.toString(jamAkhir);

        // https://www.geeksforgeeks.org/localdatetime-getminute-method-in-java-with-examples/
        int menitAwal = waktuAwal.getMinute(); // 0 - 59
        int menitAkhir = waktuAkhir.getMinute();

        String menitAwalStr = (menitAwal < 10) ? ("0"+menitAwal) : Integer.toString(menitAwal);
        String menitAkhirStr = (menitAkhir < 10) ? ("0"+menitAkhir) : Integer.toString(menitAkhir);

        String waktuAwalStr = jamAwalStr + "." + menitAwalStr;
        String waktuAkhirStr = jamAkhirStr + "." + menitAkhirStr;

        return "(" + waktuAwalStr + "-" + waktuAkhirStr + ")"; // ex: (13.05-14.05)
    }
}
