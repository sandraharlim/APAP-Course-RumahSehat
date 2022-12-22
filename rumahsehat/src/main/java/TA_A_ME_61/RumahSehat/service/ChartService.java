package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;

import java.util.List;
import java.util.Map;

public interface ChartService {
    int[] getIncome(List<AppointmentModel> listAppointment);

    List<DokterModel> getListDokter();

    List<DokterModel> getListDokterLineChart(String id1, String id2, String id3, String id4, String id5);

    Map<String, List<Integer>> getIncomeAllDokter(List<DokterModel> listDokter, List<TagihanModel> listTagihan, int tahun);
}
