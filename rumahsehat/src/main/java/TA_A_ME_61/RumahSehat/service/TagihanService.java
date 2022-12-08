package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;

import java.util.List;

public interface TagihanService {
    List<TagihanModel> getListTagihan();
    TagihanModel addTagihan(AppointmentModel appointment);

//    List<TagihanModel> getListTagihanByUuid(String uuid);


}
