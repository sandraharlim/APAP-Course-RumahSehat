package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class DokterServiceImpl implements DokterService{
    @Autowired
    DokterDb dokterDb;

    @Override
    public void addDokter(DokterModel dokter) {
        dokterDb.save(dokter);
    }

    @Override
    public List<DokterModel> getListDokter() {
        return dokterDb.findAll();
    }
}
