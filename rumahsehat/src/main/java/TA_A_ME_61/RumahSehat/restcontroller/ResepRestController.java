package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.JumlahModel;
import TA_A_ME_61.RumahSehat.model.ResepModel;
import TA_A_ME_61.RumahSehat.restmodel.JumlahRestModel;
import TA_A_ME_61.RumahSehat.restmodel.ResepRestModel;
import TA_A_ME_61.RumahSehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/resep")
public class ResepRestController {
    @Autowired
    private ResepService resepService;

    @GetMapping("/detail/{id}")
    public ResepRestModel viewDetailResep(@PathVariable("id") Long id){
        try {
            ResepModel resep = resepService.getResepById(id);
            ResepRestModel resepGet = new ResepRestModel();
            String status = "Belum Selesai";
            String apoteker = "-";
            List<String> jumlah = new ArrayList<>();

            if (resep.getIsDone()) {
                status = "Selesai";
                apoteker = resep.getApoteker().getNama();
            }


            if(resep.getListJumlah().size() != 0){
                for (JumlahModel jumlahx : resep.getListJumlah()){
                    String jumlahNya = ""+ jumlahx.getObat().getNamaObat()+ ", kuantitas: " + jumlahx.getKuantitas().toString();
                    jumlah.add(jumlahNya);
                }
            }

            resepGet.setId(String.valueOf(id));
            SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
            String date = formatter.format(Date.from(resep.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));
            resepGet.setTanggalWaktu(date);
            resepGet.setDokter(resep.getAppointment().getDokter().getNama());
            resepGet.setStatus(status);
            resepGet.setPasien(resep.getAppointment().getPasien().getNama());
            resepGet.setApoteker(apoteker);
            resepGet.setJumlah(jumlah);

            return resepGet;
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Resep " + id + " not found");
        }
    }
}
