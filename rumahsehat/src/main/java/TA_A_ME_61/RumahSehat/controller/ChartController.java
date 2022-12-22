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
import java.util.*;

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
    public String lineChartDefault(Model model){

        var date = LocalDateTime.now();
        var firstDate = LocalDateTime.of(date.getYear(), 1, 1, 0, 0);
        var lastDate = LocalDateTime.of(date.getYear(), 12, 1, 0, 0).plusMonths(1).minusMinutes(1);

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

        // Map dokter, pendapatan tiap bulan
        Map<String, List<Integer>> totalIncomeAllDokter = new LinkedHashMap<>();
        for (DokterModel dokter : listDokter) {
            List<Integer> incomePerMonthPerDokter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
            for (int i = 0; i < 12; i++) {
                int incomePerMonth = 0;
                for (TagihanModel tagihan : listTagihan){
                    if (tagihan.getIsPaid() &&
                            tagihan.getAppointment().getDokter().uuid.equals(dokter.uuid) &&
                            tagihan.getTanggalTerbuat().getYear() == tahun)
                    {
                        if (tagihan.getTanggalTerbuat().getMonthValue() == (i+1) ) {
                            incomePerMonth += tagihan.getJumlahTagihan().intValue();
                        }
                    }
                }
                incomePerMonthPerDokter.set(i, incomePerMonth);
            }
            totalIncomeAllDokter.put(dokter.getNama(), incomePerMonthPerDokter);
        }
        List<String> lstDokter = new ArrayList<String>(totalIncomeAllDokter.keySet());
        List<List<Integer>> lstIncome = new ArrayList<List<Integer>>(totalIncomeAllDokter.values());

        if (lstIncome.size() < 5) {
            List<Integer> fillZero = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
            int size = lstIncome.size();
            for (int i = 0; i < 5 - size; i++) {
                lstIncome.add(fillZero);
            }
        }

        if (lstDokter.size() < 5) {
            String fillEmpty = "Not Selected";
            int size = lstDokter.size();
            for (int i = 0; i < 5 - size; i++) {
                lstDokter.add(fillEmpty);
            }
        }

        model.addAttribute("lstDokter", lstDokter);
        model.addAttribute("lstIncome", lstIncome);
        model.addAttribute("tahun", tahun);

        return "linechart-yearly";

    }
}