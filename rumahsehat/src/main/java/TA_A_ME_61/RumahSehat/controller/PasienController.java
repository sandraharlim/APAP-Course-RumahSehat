package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PasienController {
    @Qualifier("pasienServiceImpl")
    @Autowired
    private PasienService pasienService;

    @GetMapping("/pasien/")
    public String listPasien(Model model){
        List<PasienModel> listPasien = pasienService.getListPasien();

        model.addAttribute("listPasien",listPasien);
        return "viewall-pasien";
    }

    @GetMapping("/pasien/delete/{uuid}")
    public String deletePasien(@PathVariable String uuid, Model model){
        PasienModel pasien = pasienService.getPasienByUuid(uuid);
        model.addAttribute("pasien", pasien);
        pasienService.deletePasien(pasien);
        return "delete-pasien";

    }
}
