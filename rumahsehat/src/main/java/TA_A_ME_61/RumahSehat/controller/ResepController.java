package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.JumlahModel;
import TA_A_ME_61.RumahSehat.model.ObatModel;
import TA_A_ME_61.RumahSehat.model.ResepModel;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.JumlahService;
import TA_A_ME_61.RumahSehat.service.ObatService;
import TA_A_ME_61.RumahSehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResepController {
    @Autowired
    private ResepService resepService;

    @Autowired
    private JumlahService jumlahService;

    @Autowired
    private ObatService obatService;

    @Autowired
    private AppointmentService appointmentService;


    @GetMapping("/resep/create/{idAppointment}")
    public String createResepForm(Model model, @PathVariable Long idAppointment) {
        System.out.println("masuk");
        ResepModel resep = new ResepModel();
        resep.setAppointment(appointmentService.getAppointmentById(idAppointment));
        resep.setIsDone(false);

        JumlahModel jumlah = new JumlahModel();
        ObatModel obat = new ObatModel();
        jumlah.setObat(obat);
        List<ObatModel> listObat = obatService.getDaftarObat();

        List<JumlahModel> listJumlahNew = new ArrayList<>();
        resep.setListJumlah(listJumlahNew);
        resep.getListJumlah().add(jumlah);

        model.addAttribute("resep", resep);
        model.addAttribute("listObat", listObat);
        return "form-create-resep";
    }

    @PostMapping("/resep/create")
    public String createResepSubmit(@ModelAttribute ResepModel resep, Model model) {

        for (JumlahModel jumlahx : resep.getListJumlah()) {
            jumlahx.setResep(resep);

            ObatModel obat = jumlahx.getObat();
            ObatModel obatDb = obatService.getObatByIdObat(obat.getIdObat());
            if (jumlahx.getKuantitas() > obatDb.getStok()) {
                model.addAttribute("resep", resep);
                return "gabisa-crate-resep";
            } else {
                obatDb.setStok(obatDb.getStok() - jumlahx.getKuantitas());

                jumlahx.setObat(obatDb);
            }

        }
        LocalDateTime now = LocalDateTime.now();
        resep.setCreatedAt(now);
        resep.setIsDone(false);
        resep.setAppointment(appointmentService.getAppointmentById(resep.getAppointment().getId()));
        // set apoteker dulu
//        resep.setApoteker();

        resepService.addResep(resep);
        for (JumlahModel jumlahxx : resep.getListJumlah()) {
            jumlahService.addJumlah(jumlahxx);
            ObatModel obat = jumlahxx.getObat();
            obatService.updateStok(obat);
        }

        model.addAttribute("resep", resep);
        return "create-resep";
    }

    @PostMapping(value = "/resep/create", params = {"addRowObat"})
    private String addRowJumlahMultiple(
            @ModelAttribute ResepModel resep,
            Model model
    ) {
        if (resep.getListJumlah() == null || resep.getListJumlah().size() == 0) {
            resep.setListJumlah(new ArrayList<>());
        }

        JumlahModel jumlah = new JumlahModel();
        ObatModel obat = new ObatModel();
        jumlah.setObat(obat);
        resep.getListJumlah().add(jumlah);
        List<ObatModel> listObat = obatService.getDaftarObat();


        model.addAttribute("resep", resep);
        model.addAttribute("listObat", listObat);
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

        List<ObatModel> listObat = obatService.getDaftarObat();


        model.addAttribute("resep", resep);
        model.addAttribute("listObat", listObat);
        return "form-create-resep";
    }
}
