package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.JumlahModel;
import TA_A_ME_61.RumahSehat.repository.JumlahDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class JumlahServiceImpl implements JumlahService{
    @Autowired
    JumlahDb jumlahDb;

    public List<JumlahModel> getListJumlah(){
        return jumlahDb.findAll();
    }

    public void deleteJumlahByIdObat(String idObat){
        jumlahDb.deleteJumlahModelByObat_IdObat(idObat);
    }

}
