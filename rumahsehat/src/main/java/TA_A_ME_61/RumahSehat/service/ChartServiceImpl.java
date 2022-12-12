package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ChartServiceImpl implements ChartService {
    @Override
    public int[] getIncome(List<AppointmentModel> listAppointment){
        int[] hasil = new int[12];

        for (AppointmentModel apt : listAppointment){

            hasil[apt.getWaktuAwal().getMonthValue() - 1] = apt.getDokter().getTarif().intValue();
        }
        return hasil;
    }
}
