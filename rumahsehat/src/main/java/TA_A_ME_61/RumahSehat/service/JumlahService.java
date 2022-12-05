package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.JumlahModel;

import java.util.List;

public interface JumlahService {
    List<JumlahModel> getListJumlah();

    void deleteJumlahByIdObat(String idObat);

    void addJumlah (JumlahModel jumlah);
}
