package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.service.DokterService;
import TA_A_ME_61.RumahSehat.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class GraphController {

    @Qualifier("dokterServiceImpl")
    @Autowired
    private DokterService dokterService;

    @Qualifier("tagihanServiceImpl")
    @Autowired
    private TagihanService tagihanService;

    @GetMapping("/dokter/barchart")
    public String barGraph(Model model){
        List<DokterModel> listDokter = dokterService.getListDokter();
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
