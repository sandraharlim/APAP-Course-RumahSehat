package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.*;
import TA_A_ME_61.RumahSehat.service.*;
import TA_A_ME_61.RumahSehat.restservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class GraphController {

    @Qualifier("dokterServiceImpl")
    @Autowired
    private DokterService dokterService;

    @Qualifier("tagihanServiceImpl")
    @Autowired
    private TagihanService tagihanService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dokter/barchart")
    public String pilihBarchartDanDokter(Model model) {
        List<DokterModel> listDokter = dokterService.getListDokter();
        model.addAttribute("listDokter", listDokter);
        return "form-barchart";
    }

    @GetMapping(value = "/dokter/barchart", params = {"jenis", "id1", "id2", "id3", "id4", "id5", "id6", "id7", "id8"})
    public String hasilBarchart(
        Model model,
        @RequestParam int jenis,
        @RequestParam String id1,
        @RequestParam String id2,
        @RequestParam String id3,
        @RequestParam String id4,
        @RequestParam String id5,
        @RequestParam String id6,
        @RequestParam String id7,
        @RequestParam String id8) {

        List<DokterModel> listDokter = dokterService.getListDokterBarchart(id1, id2, id3, id4, id5, id6, id7, id8);

        if (jenis == 0) { // Total Kuantitas Appointment
            return barGraphTotalKuantitas(model, listDokter);
        } else { // Total Pendapatan
            return barGraphPendapatan(model, listDokter);
        }    
    }

    public String barGraphTotalKuantitas(Model model, List<DokterModel> listDokter) {
        Map<String, Integer> totalApptDokter = appointmentService.getTotalApptDokters(listDokter);

        model.addAttribute("totalApptDokter", totalApptDokter);
        return "barchart-total-kuantitas";
    }

    public String barGraphPendapatan(Model model, List<DokterModel> listDokter){
        List<TagihanModel> listTagihan= tagihanService.getListTagihan();

        Map<String, Long> totalPendapatan = new LinkedHashMap<>();

        for (DokterModel dokter : listDokter){
            long pendapatan = 0;
            for (TagihanModel tagihan : listTagihan){
                if (tagihan.getIsPaid() && tagihan.getAppointment().getDokter().uuid.equals(dokter.uuid)){
                    pendapatan += tagihan.getJumlahTagihan();
                }
            }
            totalPendapatan.put(dokter.getNama(), pendapatan);
        }
        model.addAttribute( "totalPendapatan", totalPendapatan);
        return "barChart-total-pendapatan";
    }
}
