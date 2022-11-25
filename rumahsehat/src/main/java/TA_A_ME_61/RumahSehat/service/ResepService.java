package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.ResepModel;

import java.util.List;

public interface ResepService {
    ResepModel getResepById(Long id);

    void addResep (ResepModel resep);

    List<ResepModel> getListResep();
}
