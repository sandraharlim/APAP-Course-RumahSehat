package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.ObatModel;
import TA_A_ME_61.RumahSehat.restmodel.ObatRestModel;
import TA_A_ME_61.RumahSehat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/obat")
public class ObatController {
    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    @GetMapping("/")
    public String daftarObat(Model model){
        List<ObatModel> listObat = obatService.getDaftarObat();
        model.addAttribute("listObat", listObat);
        return "viewall-obat";
    }

    @GetMapping("/ubah-stok/{idObat}")
    public String formUbahStokObat(@PathVariable String idObat, Model model){
        ObatModel obat = obatService.getObatByIdObat(idObat);
        model.addAttribute("obat", obat);
        return "form-update-stokobat";
    }

    @PostMapping(value = "/ubah-stok/{idObat}", params = {"save"})
    public String submitUbahStokObat(@ModelAttribute ObatRestModel obat, Model model){
        ObatModel obatModel = new ObatModel();
        obatModel.setIdObat(obat.getIdObat());
        obatModel.setNamaObat(obat.getNamaObat());
        obatModel.setHarga(obat.getHarga());
        obatModel.setStok(obat.getStok());
        obatService.updateStok(obatModel);
        model.addAttribute("obat", obatModel);
        return "update-stokobat";
    }
}