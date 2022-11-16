package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.DokterModel;

import java.util.List;

public interface DokterService {
    void addDokter(DokterModel dokter);

    List<DokterModel> getListDokter();

}
