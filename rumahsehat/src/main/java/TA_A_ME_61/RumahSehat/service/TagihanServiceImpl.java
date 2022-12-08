package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagihanServiceImpl implements TagihanService{

    @Autowired
    TagihanDb tagihanDb;

    @Override
    public List<TagihanModel> getListTagihan() {
        return tagihanDb.findAll();
    }

    @Override
    public TagihanModel addTagihan(AppointmentModel appointment) {
        // TODO Auto-generated method stub
        DokterModel dokter = appointment.getDokter();
        Long tarif = dokter.getTarif();
        TagihanModel tagihan = new TagihanModel();
        tagihan.setJumlahTagihan(tarif);
        tagihan.setAppointment(appointment);
        tagihan.setTanggalTerbuat(LocalDateTime.now());
        return null;
    }

//    @Override
//    public List<TagihanModel> getListTagihanByUuid(String uuid) {
//        List<TagihanModel> allTagihan = tagihanDb.findAll();
//
//        List<TagihanModel> specificTagihan = new ArrayList<>();
//        for(TagihanModel tagihan : allTagihan){
//            if (tagihan.getAppointment().getPasien().getUuid().equalsIgnoreCase(uuid)){
//                specificTagihan.add(tagihan);
//            }
//        }
//        return specificTagihan;
//    }
}
