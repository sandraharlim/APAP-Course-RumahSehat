package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.ApotekerModel;

import java.util.List;

public interface ApotekerService {
    void addApoteker(ApotekerModel apoteker);

    List<ApotekerModel> getListApoteker();

    ApotekerModel getApotekerByUuid(String uuid);

    void deleteApoteker(ApotekerModel apoteker);

}
