package TA_A_ME_61.RumahSehat.restservice;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.repository.AppointmentDb;
import TA_A_ME_61.RumahSehat.restmodel.AppointmentRestModel;
import TA_A_ME_61.RumahSehat.restmodel.DokterDropdownItem;
import TA_A_ME_61.RumahSehat.service.DokterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppointmentRestServiceImpl implements AppointmentRestService{
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
        List<AppointmentModel> listApptSameDokter = appointmentDb.findAllByDokter(uuidDokter);
        String hasil = validasiJadwal(appointment, listApptSameDokter);
        if (hasil != null) return hasil; // tabrakan sama jadwalnya si dokter

        String uuidPasien = appointment.getPasien().getUuid();
        List<AppointmentModel> listApptSamePasien = appointmentDb.findAllByPasien(uuidPasien);
        hasil = validasiJadwal(appointment, listApptSamePasien);
        if (hasil != null) return hasil; // tabrakan sama jadwalnya si pasien

        return "Valid";
    }
    private String validasiJadwal(AppointmentModel appointment, List<AppointmentModel> listOldAppt) {
        LocalDateTime waktuAwalNewAppt = appointment.getWaktuAwal();
        LocalDateTime waktuAkhirNewAppt = waktuAwalNewAppt.plusHours(1); // https://www.geeksforgeeks.org/localdatetime-plushours-method-in-java-with-examples/

        int yearNewAppt = waktuAwalNewAppt.getYear();
        int dayOfYearNewAppt = waktuAwalNewAppt.getDayOfYear();

        for (AppointmentModel appt : listOldAppt) {
            // "old" disini maksutnya appt yg udh terdaftar di DB,
            // bukan berarti waktu janjian pasti sebelum si NewAppt
            LocalDateTime waktuAwalOldAppt = appt.getWaktuAwal();
            LocalDateTime waktuAkhirOldAppt = appt.getWaktuAwal().plusHours(1);

            int yearOldAppt = waktuAwalOldAppt.getYear();
            int dayOfYearOldAppt = waktuAwalOldAppt.getDayOfYear();

            Boolean sameYear = (yearNewAppt == yearOldAppt);
            Boolean sameDayOfYear = (dayOfYearNewAppt == dayOfYearOldAppt);

            if (sameYear && sameDayOfYear) {
                Boolean tabrakanPrevAppt = waktuAwalNewAppt.isBefore(waktuAkhirOldAppt) && waktuAwalOldAppt.isBefore(waktuAwalNewAppt); // tabrakan dengan appt sebelumnya
                Boolean tabrakanNextAppt = waktuAkhirNewAppt.isAfter(waktuAwalOldAppt) && waktuAkhirOldAppt.isAfter(waktuAkhirNewAppt); // tabrakan dengan appt setelahnya
                Boolean tabrakanCurrAppt = waktuAwalNewAppt.isEqual(waktuAwalOldAppt); // tabrakan dengan appt di waktu yg sama

                if (tabrakanPrevAppt || tabrakanNextAppt || tabrakanCurrAppt) { // ga valid
                    String rangeWaktuOldAppt = getWaktuAwalWaktuAkhir(appt);
                    String rangeWaktuNewAppt = getWaktuAwalWaktuAkhir(appointment);
                    // Kalau error akan ditampilkan pesan:
                    // Appointment A (13.05-14.05), akan dibuat Appointment B dari jam (13.45-14.45)
                    return "Appointment baru di jam " + rangeWaktuNewAppt +
                            " memiliki konflik jadwal dengan Appointment " +
                            appt.getKode() + " di jam " + rangeWaktuOldAppt + ".";
                }
            }
        }
        // valid
        return null;
    }

    @Override
    public String getWaktuAwalWaktuAkhir(AppointmentModel appointment) { // ex: (13.05-14.05)
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

    @Override
    public List<DokterDropdownItem> getDokterDropdownItems(List<DokterModel> listDokter) {

        List<DokterDropdownItem> data = new ArrayList<>();
        for (DokterModel dokter : listDokter) {
            DokterDropdownItem dokterDropdownItem = new DokterDropdownItem();
            dokterDropdownItem.setUuid(dokter.getUuid());
            dokterDropdownItem.setNamaTarif(getNamaTarif(dokter));

            data.add(dokterDropdownItem);
        }

        return data;
    }

    private String getNamaTarif(DokterModel dokter) {
        return dokter.getNama() + "-" + dokter.getTarif();
    }

    @Override
    public LocalDateTime convertWaktuAwalFromFlutter(String date, String time) {
        LocalDateTime waktuAwal = LocalDateTime.now();

        time = convertTo24(time);
        date = convertDate(date);

        if (time == null) { // ada kesalahan input jam (>23) / menit (>59)
            return null;
        }

        // "yyyy-MM-dd'T'HH:mm"

        String waktuAwalStr = date + "T" + time;
        waktuAwal = LocalDateTime.parse(waktuAwalStr);

        return waktuAwal;
    }

    private String convertTo24(String time) {
        // 12:23 AM or 04:35 PM
        String amOrFm = time.substring(time.length()-2); // ambil 2 char terakhir
        time = time.substring(0, 5); // 12:23 (asumsi kalo hh mm nya pasti 2 digit)
        String hh = time.substring(0, 2);
        String mm = time.substring(3);

        if (amOrFm.equals("PM")) {
            int hhInteger = Integer.parseInt(hh);
            hhInteger += 12;
            if (hhInteger>23) {
                System.out.println("Error: jam bernilai " + hhInteger + " (lebih dari 23)");
                return null;
            }
            hh = String.valueOf(hhInteger);
        }

        int mmInteger = Integer.parseInt(mm);
        if (mmInteger > 59) {
            System.out.println("Error: menit bernilai " + mmInteger + " (lebih dari 59)");
            return null;
        }

        return hh + ":" + mm;
    }

    private String convertDate(String date) { // yyyy-MM-dd
        String[] splittedDate = date.split("/"); // 12/21/2022
        String yyyy = splittedDate[2];
        String mm = splittedDate[0];
        mm = (mm.length() == 1) ? ("0" + mm) : mm;
        String dd = splittedDate[1];
        dd = (dd.length() == 1) ? ("0" + dd) : dd;

        return yyyy + "-" + mm + "-" + dd;
    }

    // ========= method untuk fitur 7: view all appt ============

    @Override
    public List<AppointmentRestModel> convertApptToRestAppt(List<AppointmentModel> listAppointment) {
        List<AppointmentRestModel> response = new ArrayList<>();

        for (AppointmentModel appt: listAppointment) {
            AppointmentRestModel apptRest = new AppointmentRestModel();
            apptRest.setKode(appt.getKode());
            apptRest.setDokter(appt.getDokter().getNama());
            apptRest.setPasien(appt.getPasien().getNama());
            LocalDateTime waktuAwal = appt.getWaktuAwal();
            String waktuAwalStr = convertLocalDateTimeToString(waktuAwal);
            apptRest.setWaktuAwal(waktuAwalStr);
            String status = (appt.getIsDone()) ? "Sudah Selesai" : "Belum Dimulai";
            apptRest.setStatus(status);
            if (appt.getResep() != null){
                apptRest.setResepId(appt.getResep().getId().toString());
            }
            else {
                apptRest.setResepId("0");
            }

            response.add(apptRest);
        }

        return response;
    }

    private String convertLocalDateTimeToString(LocalDateTime waktuAwal) {
        String waktuAwalStr = ""; // 'dd MMMM yyyy HH:mm'
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        waktuAwalStr = waktuAwal.format(formatter);

        return waktuAwalStr;
    }
}
