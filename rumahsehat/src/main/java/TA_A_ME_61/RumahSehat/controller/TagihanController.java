package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TagihanController {
    @Qualifier("tagihanServiceImpl")
    @Autowired
    private TagihanService tagihanService;


    @GetMapping("/tagihan/viewall")
    public String listTagihan(Model model){
        List<TagihanModel> listTagihan = tagihanService.getListTagihan();


        model.addAttribute("listTagihan",listTagihan);
        return "viewall-tagihan";
    }
}
