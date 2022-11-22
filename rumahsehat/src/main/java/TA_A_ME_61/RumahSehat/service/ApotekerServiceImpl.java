package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.ApotekerModel;
import TA_A_ME_61.RumahSehat.repository.ApotekerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApotekerServiceImpl implements ApotekerService{
    @Autowired
    ApotekerDb apotekerDb;

    @Override
    public void addApoteker(ApotekerModel apoteker) {
        apotekerDb.save(apoteker);
    }

    @Override
    public List<ApotekerModel> getListApoteker() {
        return apotekerDb.findAll();
    }

    @Override
    public ApotekerModel getApotekerByUuid(String uuid) {
        Optional<ApotekerModel> apoteker = apotekerDb.findByUuid(uuid);
        if (apoteker.isPresent()) {
            return apoteker.get();
        } else {
            return null;
        }
    }

    @Override
    public void deleteApoteker(ApotekerModel apoteker) {
        apotekerDb.delete(apoteker);
    }


}
