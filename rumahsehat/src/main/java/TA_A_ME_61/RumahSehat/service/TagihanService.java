package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.TagihanModel;

import java.util.List;

public interface TagihanService {
    List<TagihanModel> getListTagihan();
    void addTagihan(TagihanModel tagihan);
    TagihanModel getTagihanById(Long id);

//    List<TagihanModel> getListTagihanByUuid(String uuid);


}
