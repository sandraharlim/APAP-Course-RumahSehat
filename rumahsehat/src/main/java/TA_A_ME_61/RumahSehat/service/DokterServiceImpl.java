package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.repository.AppointmentDb;
import TA_A_ME_61.RumahSehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DokterServiceImpl implements DokterService{
    @Autowired
    DokterDb dokterDb;

    @Autowired
    AppointmentDb appointmentDb;

    @Override
    public void addDokter(DokterModel dokter) {
        String encryptedPass = encrypt(dokter.getPassword());
        dokter.setPassword(encryptedPass);
        dokterDb.save(dokter);
    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<DokterModel> getListDokter() {
        return dokterDb.findAll();
    }

    @Override
    public DokterModel getDokterByUuid(String uuid) {
        Optional<DokterModel> dokter = dokterDb.findByUuid(uuid);
        if (dokter.isPresent()) {
            return dokter.get();
        } else {
            return null;
        }
    }

    @Override
    public void updateDokter(DokterModel dokter){
        dokterDb.save(dokter);
    }

    @Override
    public void deleteDokter(DokterModel dokter) {
        dokterDb.delete(dokter);
    }

    @Override
    public DokterModel getDokterByUsername(String username){
        return dokterDb.findByUsername(username);
    }

    @Override
    public List<DokterModel> getListDokterBarchart(String id1, String id2, String id3, String id4, String id5,
            String id6, String id7, String id8) {
        // TODO Auto-generated method stub
        List<DokterModel> listDokter = new ArrayList<>();
        listDokter = addDokterToList(listDokter, id1);
        listDokter = addDokterToList(listDokter, id2);
        listDokter = addDokterToList(listDokter, id3);
        listDokter = addDokterToList(listDokter, id4);
        listDokter = addDokterToList(listDokter, id5);
        listDokter = addDokterToList(listDokter, id6);
        listDokter = addDokterToList(listDokter, id7);
        listDokter = addDokterToList(listDokter, id8);

        return listDokter;
    }

    private List<DokterModel> addDokterToList(List<DokterModel> listDokter, String id) {
        DokterModel dokter = getDokterByUuid(id);
        if (dokter != null) {
            listDokter.add(dokter);
        }
        return listDokter;
    }

    public List getPendapatanBulan(int bulan,int tahun, String username){
        List<AppointmentModel> appointmentList = appointmentDb.findAllByDokter_UsernameAndIsDoneAndTagihan_IsPaid(username, true,true);
        List<HashMap<String, Integer>> pendapatanDataRaw = new ArrayList<HashMap<String, Integer>>();
        for (AppointmentModel appt : appointmentList){
            if(appt.getTagihan().getTanggalBayar() != null && appt.getTagihan().getTanggalBayar().getMonthValue() == bulan
                    && appt.getTagihan().getTanggalBayar().getYear() == tahun){
                HashMap<String, Integer> tagihan = new HashMap<>();
                tagihan.put("tanggal",appt.getTagihan().getTanggalBayar().getDayOfMonth());
                tagihan.put("pendapatan",(appt.getDokter().getTarif()).intValue());
                pendapatanDataRaw.add(tagihan);
            }
        }
        List<HashMap<String, Integer>> pendapatanDataFinal = new ArrayList<HashMap<String, Integer>>();
        for (int i = 1; i <= YearMonth.of(tahun, 2).lengthOfMonth(); i++){
            int sum = 0;
            for (HashMap<String, Integer> rawData : pendapatanDataRaw){
                if (rawData.get("tanggal") == i){
                    sum += rawData.get("pendapatan");
                }
            }
            HashMap<String, Integer> datePendapatan = new HashMap<>();
            datePendapatan.put("tanggal",i);
            datePendapatan.put("pendapatan",sum);
            pendapatanDataFinal.add(datePendapatan);
        }

        return pendapatanDataFinal;
    }

    public HashMap<String,String> getDokterName(String username){
        Optional<DokterModel> doctor = Optional.ofNullable(dokterDb.findByUsername(username));
        HashMap<String,String> map = new HashMap<>();
        map.put("name",doctor.get().getNama());
        return map;
    }

}
