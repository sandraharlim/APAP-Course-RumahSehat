package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {

    @Autowired
    DokterDb dokterDb;
    @Override
    public int[] getIncome(List<AppointmentModel> listAppointment){
        var hasil = new int[12];

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

    @Override
    public Map<String, List<Integer>> getIncomeAllDokter(List<DokterModel> listDokter, List<TagihanModel> listTagihan, int tahun) {
        Map<String, List<Integer>> totalIncomeAllDokter = new LinkedHashMap<>();
        for (DokterModel dokter : listDokter) {
            List<Integer> incomePerMonthPerDokter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
            for (var i = 0; i < 12; i++) {
                var incomePerMonth = 0;
                for (TagihanModel tagihan : listTagihan){
                    if (tagihan.getIsPaid() &&
                            tagihan.getAppointment().getDokter().uuid.equals(dokter.uuid) &&
                            tagihan.getTanggalTerbuat().getYear() == tahun &&
                            tagihan.getTanggalTerbuat().getMonthValue() == (i+1))
                    {
                        incomePerMonth += tagihan.getJumlahTagihan().intValue();
                    }
                }
                incomePerMonthPerDokter.set(i, incomePerMonth);
            }
            totalIncomeAllDokter.put(dokter.getNama(), incomePerMonthPerDokter);
        }
        return  totalIncomeAllDokter;
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
