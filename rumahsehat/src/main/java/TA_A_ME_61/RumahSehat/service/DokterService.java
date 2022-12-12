package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.DokterModel;

import java.util.List;

public interface DokterService {
    void addDokter(DokterModel dokter);

    List<DokterModel> getListDokter();
    
    List<DokterModel> getListDokterBarchart(String id1, String id2,String id3,String id4,String id5,String id6,String id7,String id8);

    DokterModel getDokterByUuid(String uuid);

    void updateDokter(DokterModel dokter);

    void deleteDokter(DokterModel dokter);

    DokterModel getDokterByUsername(String username);

    List<DokterModel> findAllDokter();


}
