package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.ObatModel;

import java.util.List;

public interface ObatService {
    List<ObatModel> getDaftarObat();

    ObatModel getObatByIdObat(String idObat);

    ObatModel updateStok(ObatModel updatedObat);
}
