package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.restmodel.TagihanRestModel;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.PasienService;
import TA_A_ME_61.RumahSehat.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/pasien")
public class TagihanRestController {
    @Autowired
    private TagihanService tagihanService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PasienService pasienService;

    @GetMapping(value = "/tagihan")
    public List<TagihanRestModel> listTagihan() {
        log.info("User mencoba melihat seluruh tagihan yang dimiliki");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        PasienModel pasien = pasienService.getPasienByUsername(username);

        List<AppointmentModel> listAppointment = new ArrayList<>();
        List<TagihanRestModel> listTagihan = new ArrayList<>();

        if (pasien != null) {
            listAppointment = appointmentService.getAllApptByPasien(pasien);
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
        }

        for (AppointmentModel appointment : listAppointment){
            TagihanModel tagihan = appointment.getTagihan();
            if (tagihan == null) {

            } else {
                var tagihanRest = new TagihanRestModel();
                tagihanRest.setIsPaid(tagihan.getIsPaid());
                tagihanRest.setJumlahTagihan(tagihan.getJumlahTagihan());
                tagihanRest.setKode(tagihan.getKode());
                if (tagihan.getTanggalBayar() != null) {
                    String tglBayar = ""; // 'dd MMMM yyyy HH:mm'
                    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
                    tglBayar = tagihan.getTanggalBayar().format(formatter);
                    
                    tagihanRest.setTanggalBayar(tglBayar);
                }

                String tglTerbuat = ""; // 'dd MMMM yyyy HH:mm'
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
                tglTerbuat = tagihan.getTanggalTerbuat().format(formatter);

                tagihanRest.setTanggalTerbuat(tglTerbuat);

                listTagihan.add(tagihanRest);
            }
        }

        return listTagihan;
    }

    @PostMapping("/tagihan/{kode}/bayar")
    public String bayarTagihan(@PathVariable String kode) {
        log.info("User mencoba melakukan pembayaran tagihan");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        PasienModel pasien = pasienService.getPasienByUsername(username);

        TagihanModel tagihan = tagihanService.getTagihanByKode(kode);

        if (tagihanService.bayarTagihan(tagihan, pasien)) { // berhasil
            return "Berhasil";
        }
        return "Gagal, saldo tidak mencukupi";
    }

}




