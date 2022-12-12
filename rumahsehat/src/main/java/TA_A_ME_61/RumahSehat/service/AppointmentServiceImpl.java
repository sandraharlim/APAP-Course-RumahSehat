package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.repository.AppointmentDb;
import TA_A_ME_61.RumahSehat.restmodel.DokterDropdownItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    AppointmentDb appointmentDb;

    @Autowired
    DokterService dokterService;

    // ========= method untuk fitur 7: view all appt ============

    @Override
    public List<AppointmentModel> getAllAppt() {
        return appointmentDb.findAll();
    }

    @Override
    public List<AppointmentModel> getAllApptByDokter(DokterModel dokter) {
        return appointmentDb.findAllByDokter(dokter.getUuid());
    }

    @Override
    public List<AppointmentModel> getAllApptByPasien(PasienModel pasien) {
        return appointmentDb.findAllByPasien(pasien.getUuid());
    }
    @Override
    public AppointmentModel getAppointmentById(Long id){
        Optional<AppointmentModel> appointment = appointmentDb.findById(id);
        if(appointment.isPresent()){
            return appointment.get();
        }
        return null;
    }

    @Override
    public AppointmentModel getAppointmentByKode(String kode){
        return appointmentDb.getAppointmentModelByKode(kode).orElse(null);
    }

    @Override
    public void finishAppointment(AppointmentModel appointment) {
        appointment.setIsDone(true);
        appointmentDb.save(appointment);
    }

    @Override
    public void addAppointment(AppointmentModel appointment) {
        appointmentDb.save(appointment);
    }
}

