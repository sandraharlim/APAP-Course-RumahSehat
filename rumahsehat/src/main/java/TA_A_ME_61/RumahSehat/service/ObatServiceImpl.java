package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.ObatModel;
import TA_A_ME_61.RumahSehat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ObatServiceImpl implements ObatService{
    @Autowired
    ObatDb obatDb;

    public List<ObatModel> getDaftarObat(){
        return obatDb.findAll();
    }

    public ObatModel getObatByIdObat(String idObat){
        return obatDb.findById(idObat).orElse(null);
    }

    public ObatModel updateStok(ObatModel updatedObat){
        return obatDb.save(updatedObat);
    }

}
