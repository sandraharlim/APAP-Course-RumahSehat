package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {

    @Autowired
    DokterDb dokterDb;
    @Override
    public int[] getIncome(List<AppointmentModel> listAppointment){
        int[] hasil = new int[12];

        for (AppointmentModel apt : listAppointment){

            hasil[apt.getWaktuAwal().getMonthValue() - 1] = apt.getDokter().getTarif().intValue();
        }
        return hasil;
    }

    @Override
    public List<DokterModel> getListDokter() {
        return dokterDb.findAll();
    }

    @Override
    public List<DokterModel> getListDokterLineChart(String id1, String id2, String id3, String id4, String id5) {
        List<DokterModel> listDokter = new ArrayList<>();
        addDokterToList(listDokter, id1);
        addDokterToList(listDokter, id2);
        addDokterToList(listDokter, id3);
        addDokterToList(listDokter, id4);
        addDokterToList(listDokter, id5);

        return listDokter;
    }

    private List<DokterModel> addDokterToList(List<DokterModel> listDokter, String id) {
        DokterModel dokter = getDokterByUuid(id);
        if (dokter != null) {
            listDokter.add(dokter);
        }
        return listDokter;
    }

    public DokterModel getDokterByUuid(String uuid) {
        Optional<DokterModel> dokter = dokterDb.findByUuid(uuid);
        if (dokter.isPresent()) {
            return dokter.get();
        } else {
            return null;
        }
    }
}
