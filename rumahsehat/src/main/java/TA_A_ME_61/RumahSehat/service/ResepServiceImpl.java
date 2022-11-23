package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.ResepModel;
import TA_A_ME_61.RumahSehat.repository.ResepDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ResepServiceImpl implements ResepService{
    @Autowired
    ResepDb resepDb;

    public ResepModel getResepById(Long id){
        return resepDb.findById(id).orElse(null);
    }

    @Override
    public void addResep(ResepModel resep) {
        resepDb.save(resep);
    }
}
