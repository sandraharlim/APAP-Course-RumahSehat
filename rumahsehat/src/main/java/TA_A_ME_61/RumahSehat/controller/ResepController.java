package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.JumlahModel;
import TA_A_ME_61.RumahSehat.model.ObatModel;
import TA_A_ME_61.RumahSehat.model.ResepModel;
import TA_A_ME_61.RumahSehat.service.JumlahService;
import TA_A_ME_61.RumahSehat.service.ObatService;
import TA_A_ME_61.RumahSehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResepController {
    @Autowired
    private ResepService resepService;

    @Autowired
    private JumlahService jumlahService;

    @Autowired
    private ObatService obatService;


    @GetMapping("/resep/create")
    public String createResepForm(Model model) {
        ResepModel resep = new ResepModel();
        resep.setIsDone(false);

        JumlahModel jumlah = new JumlahModel();
        List<ObatModel> listObat = obatService.getDaftarObat();

        List<JumlahModel> listJumlahNew = new ArrayList<>();
        resep.setListJumlah(listJumlahNew);
        resep.getListJumlah().add(new JumlahModel());


        model.addAttribute("resep", resep);
        model.addAttribute("jumlah", jumlah);
        model.addAttribute("listObat", listObat);
        return "form-create-resep";
    }

    @PostMapping("/resep/create")
    public String createResepSubmit(@ModelAttribute ResepModel resep, @ModelAttribute JumlahModel jumlah, Model model) {
        jumlahService.addJumlah(jumlah);

        if (resep.getListJumlah() == null) {
            resep.setListJumlah(new ArrayList<>());
        } else {
            for (JumlahModel jumlahx : resep.getListJumlah()) {
                jumlahx.setResep(resep);
                ObatModel obat = jumlah.getObat();
                obat.setStok(obat.getStok() - jumlah.getKuantitas());
            }
        }

        LocalDateTime now = LocalDateTime.now();
        resep.setCreatedAt(now);

        resepService.addResep(resep);
        model.addAttribute("resep", resep);
        return "done-create-resep";
    }

    @PostMapping(value = "/resep/create", params = {"addRowObat"})
    private String addRowJumlahMultiple(
            @ModelAttribute ResepModel resep,
            Model model
    ) {
        if (resep.getListJumlah() == null || resep.getListJumlah().size() == 0) {
            resep.setListJumlah(new ArrayList<>());
        }
        resep.getListJumlah().add(new JumlahModel());
        List<JumlahModel> listJumlah = jumlahService.getListJumlah();

        model.addAttribute("resep", resep);
//        model.addAttribute("listPengajarExisting", listJumlah);
        return "form-create-resep";
    }

    @PostMapping(value = "/resep/create", params = {"deleteRowObat"})
    private String deleteRowJumlahMultiple(
            @ModelAttribute ResepModel resep,
            @RequestParam("deleteRowObat") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        resep.getListJumlah().remove(rowId.intValue());

        List<JumlahModel> listJumlah = jumlahService.getListJumlah();

        model.addAttribute("resep", resep);
//        model.addAttribute("listPengajarExisting", listPegajar);
        return "form-create-resep";
    }
}
