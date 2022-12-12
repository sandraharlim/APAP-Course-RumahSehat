package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.ChartService;
import TA_A_ME_61.RumahSehat.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    ChartService chartService;

    @Autowired
    TagihanService tagihanService;


    @GetMapping("/line/default")
    private String lineChartDefault(Model model){

        LocalDateTime date = LocalDateTime.now();
        LocalDateTime firstDate = LocalDateTime.of(date.getYear(), 1, 1, 0, 0);
        LocalDateTime lastDate = LocalDateTime.of(date.getYear(), 12, 1, 0, 0).plusMonths(1).minusMinutes(1);

        List<AppointmentModel> listAppointment = appointmentService.getAllAptAnnual(firstDate, lastDate);

        model.addAttribute("pendapatan", chartService.getIncome(listAppointment));
        model.addAttribute("tahun", date.getYear());

        return "linechart-default";
    }

    @GetMapping("/line/tahunan")
    public String pilihDokterdanTahun(Model model) {
        List<DokterModel> listDokter = chartService.getListDokter();
        model.addAttribute("listDokter", listDokter);
        return "form-linechart-yearly";
    }

    @GetMapping(value = "/line/tahunan", params = {"tahun", "id1", "id2", "id3", "id4", "id5"})
    public String showLineChart(
            Model model,
            @RequestParam int tahun,
            @RequestParam String id1,
            @RequestParam String id2,
            @RequestParam String id3,
            @RequestParam String id4,
            @RequestParam String id5) {

        List<DokterModel> listDokter = chartService.getListDokterLineChart(id1, id2, id3, id4, id5);
        List<TagihanModel> listTagihan = tagihanService.getListTagihan();

        // Assume first tagihan from 2020
        // Map dari total income.
        // {"2020":{"dokter1":[20000,200000,0]}, etc}
//        Map<String,Map<String, int[]>> yearlyIncome = new LinkedHashMap<>();

        // Map dokter, pendapatan tiap bulan
        Map<String, int[]> totalIncomeAllDokter = new LinkedHashMap<>();

        for (DokterModel dokter : listDokter) {
            int[] incomePerMonthPerDokter = new int[12];
            for (int i = 0; i < 12; i++) {
                int incomePerMonth = 0;
                for (TagihanModel tagihan : listTagihan){
                    if (tagihan.getIsPaid() &&
                            tagihan.getAppointment().getDokter().uuid.equals(dokter.uuid) &&
                            tagihan.getTanggalTerbuat().getYear() == tahun)
                    {
                        if (tagihan.getTanggalTerbuat().getMonthValue() == (i+1) ) {
                            incomePerMonth += tagihan.getJumlahTagihan();
                        }
                    }
                }
                incomePerMonthPerDokter[i] = incomePerMonth;
            }
            totalIncomeAllDokter.put(dokter.getNama(), incomePerMonthPerDokter);
        }

        model.addAttribute("income", totalIncomeAllDokter);
        model.addAttribute("tahun", tahun);

        return "linechart-yearly";

    }
}