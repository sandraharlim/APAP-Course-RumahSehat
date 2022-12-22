package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    ApotekerService apotekerService;

    @Autowired
    private TagihanService tagihanService;

    @GetMapping("/resep/create/{idAppointment}")
    public String createResepForm(Model model, @PathVariable Long idAppointment) {
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
        }

        LocalDateTime now = LocalDateTime.now();
        resep.setCreatedAt(now);
        resep.setIsDone(false);
        resep.setAppointment(appointmentService.getAppointmentById(resep.getAppointment().getId()));
        resepService.addResep(resep);

        for (JumlahModel jumlahxx : resep.getListJumlah()) {
            jumlahService.addJumlah(jumlahxx);
        }

        model.addAttribute("resepMade", resep);
        return "create-resep";
    }

    @PostMapping(value = "/resep/create", params = {"addRowObat"})
    public String addRowJumlahMultiple(
            @ModelAttribute ResepModel resep,
            Model model
    ) {
        if (resep.getListJumlah() == null || resep.getListJumlah().isEmpty()) {
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
    public String deleteRowJumlahMultiple(
            @ModelAttribute ResepModel resep,
            @RequestParam("deleteRowObat") Integer row,
            Model model
    ) {
        final Integer rowId = row;
        resep.getListJumlah().remove(rowId);

        List<ObatModel> listObat = obatService.getDaftarObat();


        model.addAttribute("resep", resep);
        model.addAttribute("listObat", listObat);
        return "form-create-resep";
    }

    @GetMapping("/resep")
    public String viewAllResep(Model model) {
        List<ResepModel> listResep = resepService.getListResep();

        model.addAttribute("listResep", listResep);
        return "viewall-resep";
    }

    @GetMapping("/resep/detail/{id}")
    public String viewDetailResep(@PathVariable Long id, Model model) {
        ResepModel resep = resepService.getResepById(id);

        String namaApoteker = "-";
        String namaDokter = resep.getAppointment().getDokter().getNama();
        String namaPasien = resep.getAppointment().getPasien().getNama();

        List<JumlahModel> listJumlah = resep.getListJumlah();
        String status = "Belum Selesai";

        if(resep.getIsDone()){
            status = "Selesai";
            namaApoteker = resep.getApoteker().getNama();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ApotekerModel apoteker = apotekerService.getApotekerByUsername(username);

        if (apoteker != null){
            model.addAttribute("role", "Apoteker");
        }

        model.addAttribute("resepIn", resep);
        model.addAttribute("namaApoteker", namaApoteker);
        model.addAttribute("namaDokter", namaDokter);
        model.addAttribute("namaPasien", namaPasien);
        model.addAttribute("status", status);
        model.addAttribute("listJumlah", listJumlah);
        return "view-resep";
    }

    @PostMapping("/resep/confirmation")
    public String konfirmasiResep(@ModelAttribute ResepModel resep,
                                  Model model) {
        ResepModel resepnow = resepService.getResepById(resep.getId());
        int bayarTagihan = 0;

        for (JumlahModel jumlahx : resepnow.getListJumlah()) {
            jumlahx.setResep(resepnow);

            ObatModel obat = jumlahx.getObat();
            ObatModel obatDb = obatService.getObatByIdObat(obat.getIdObat());

            if (jumlahx.getKuantitas() > obatDb.getStok()) {
                return "gabisa-konfirmasi-resep";
            } else {
                obatDb.setStok(obatDb.getStok() - jumlahx.getKuantitas());
                jumlahx.setObat(obatDb);
                bayarTagihan = bayarTagihan + (obatDb.getHarga()*jumlahx.getKuantitas());
            }

        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ApotekerModel apoteker = apotekerService.getApotekerByUsername(username);

        resepnow.setIsDone(true);
        resepnow.setApoteker(apoteker);
        resepService.addResep(resepnow);
        Long idAppointment = resepnow.getAppointment().getId();

        AppointmentModel appointment = appointmentService.getAppointmentById(idAppointment);
        appointment.setIsDone(true);
        appointment.setResep(resepnow);
        appointmentService.addAppointment(appointment);

        //Ngeset tagihannya
        TagihanModel tagihan = new TagihanModel();
        tagihan.setAppointment(resepnow.getAppointment());
        tagihan.setIsPaid(false);
        LocalDateTime now = LocalDateTime.now();
        tagihan.setTanggalTerbuat(now);
        bayarTagihan = bayarTagihan + (appointment.getDokter().getTarif()).intValue();
        tagihan.setJumlahTagihan(Long.valueOf(bayarTagihan));
        tagihanService.addTagihan(tagihan);

        TagihanModel tagihannow = tagihanService.getTagihanById(tagihan.getId());
        tagihannow.setKode("BILL-"+ tagihannow.getId());
        tagihanService.addTagihan(tagihannow);

        appointment.setTagihan(tagihanService.getTagihanById(tagihannow.getId()));
        appointmentService.addAppointment(appointment);

        model.addAttribute("resepNow", resepnow);
        return "konfirmasi-resep";
    }
}
