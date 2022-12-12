package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;

import java.util.List;

public interface ChartService {
    int[] getIncome(List<AppointmentModel> listAppointment);

    List<DokterModel> getListDokter();

    List<DokterModel> getListDokterLineChart(String id1, String id2, String id3, String id4, String id5);
}
