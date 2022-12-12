package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {
    @Override
    public Long[] getIncome(List<AppointmentModel> listAppointment){
        Long[] hasil = new Long[12];

        for (AppointmentModel apt : listAppointment){
            hasil[apt.getWaktuAwal().getMonthValue() - 1] = apt.getDokter().getTarif();
        }
        return hasil;
    }
}
