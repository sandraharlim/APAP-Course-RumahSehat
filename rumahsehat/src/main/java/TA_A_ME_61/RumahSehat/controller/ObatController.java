package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.JumlahModel;
import TA_A_ME_61.RumahSehat.model.ObatModel;
import TA_A_ME_61.RumahSehat.model.ResepModel;
import TA_A_ME_61.RumahSehat.service.JumlahService;
import TA_A_ME_61.RumahSehat.service.ObatService;
import TA_A_ME_61.RumahSehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/obat")
public class ObatController {
    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    @Autowired
    private ResepService resepService;

    @Autowired
    private JumlahService jumlahService;

    @GetMapping("/")
    private String daftarObat(Model model){
        List<ObatModel> listObat = obatService.getDaftarObat();
        model.addAttribute("listObat", listObat);
        return "viewall-obat";
    }

    @GetMapping("/ubah-stok/{idObat}")
    private String formUbahStokObat(@PathVariable String idObat, Model model){
        ObatModel obat = obatService.getObatByIdObat(idObat);
        model.addAttribute("obat", obat);
//        List<ObatModel> listObat = obatService.getDaftarObat();
//        model.addAttribute("listObatExisting", listObat);
        return "form-update-stokobat";
    }

    @PostMapping(value = "/ubah-stok/{idObat}", params = {"save"})
    private String submitUbahStokObat(@ModelAttribute ObatModel obat, Model model){
        jumlahService.deleteJumlahByIdObat(obat.getIdObat());
        if (obat.getListJumlah() == null || obat.getListJumlah().size() == 0){
            obat.setListJumlah(new ArrayList<>());
        }
        else {
            List<JumlahModel> listJumlah = obat.getListJumlah();
            obat.setListJumlah(new ArrayList<>());
            obatService.updateStok(obat);
            for(JumlahModel jumlah : listJumlah){
                jumlah.setObat(obat);
                ResepModel resep = resepService.getResepById(jumlah.getResep().getId());
                jumlah.setResep(resep);
            }
            obat.setListJumlah(listJumlah);
        }
        obatService.updateStok(obat);
        model.addAttribute("obat", obat);
        return "update-stokobat";
    }
}
