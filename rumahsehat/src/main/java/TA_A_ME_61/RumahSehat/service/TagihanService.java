package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;

import java.util.List;

public interface TagihanService {
    List<TagihanModel> getListTagihan();
    void addTagihan(TagihanModel tagihan);
    TagihanModel getTagihanById(Long id);
    TagihanModel addTagihanByDokter(AppointmentModel appointment);
    TagihanModel getTagihanByKode(String kode);
    Boolean bayarTagihan(TagihanModel tagihan, PasienModel pasien);

}
