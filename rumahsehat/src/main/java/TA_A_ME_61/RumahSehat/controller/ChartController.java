package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.ChartService;
import TA_A_ME_61.RumahSehat.service.DokterService;
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
    DokterService dokterService;

    @Autowired
    TagihanService tagihanService;

    private static final String strTahun = "tahun";

    @GetMapping("/line/default")
    public String lineChartDefault(Model model){

        var date = LocalDateTime.now();
        var firstDate = LocalDateTime.of(date.getYear(), 1, 1, 0, 0);
        var lastDate = LocalDateTime.of(date.getYear(), 12, 1, 0, 0).plusMonths(1).minusMinutes(1);

        List<AppointmentModel> listAppointment = appointmentService.getAllAptAnnual(firstDate, lastDate);

        model.addAttribute("pendapatan", chartService.getIncome(listAppointment));
        model.addAttribute(strTahun, date.getYear());

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

        Map<String, List<Integer>> totalIncomeAllDokter = chartService.getIncomeAllDokter(listDokter, listTagihan, tahun);
        List<String> lstDokter = new ArrayList<>(totalIncomeAllDokter.keySet());
        List<List<Integer>> lstIncome = new ArrayList<>(totalIncomeAllDokter.values());

        if (lstIncome.size() < 5) {
            List<Integer> fillZero = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
            int size = lstIncome.size();
            for (var i = 0; i < 5 - size; i++) {
                lstIncome.add(fillZero);
            }
        }

        if (lstDokter.size() < 5) {
            var fillEmpty = "Not Selected";
            int size = lstDokter.size();
            for (var i = 0; i < 5 - size; i++) {
                lstDokter.add(fillEmpty);
            }
        }

        model.addAttribute("lstDokter", lstDokter);
        model.addAttribute("lstIncome", lstIncome);
        model.addAttribute(strTahun, tahun);

        return "linechart-yearly";

    }

    @GetMapping("/line/bulanan")
    public String viewPemasukanDokterChartTahun(Model model) {
        List<DokterModel> listDokter = dokterService.getListDokter();
        model.addAttribute("listDokter", listDokter);
        return "form-linechart-monthly";
    }

    @GetMapping(value = "/line/bulanan", params = {"bulan","tahun", "id1", "id2", "id3", "id4", "id5"})
    public String showLineChart(
            Model model,
            @RequestParam int bulan,
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
            List<Integer> incomePerDayPerDokter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
            for (var i = 0; i < 30; i++) {
                var incomePerDay = 0;
                for (TagihanModel tagihan : listTagihan){
                    if (tagihanService.getTagihanById(tagihan.getId()).getAppointment().getDokter().getUsername().equals(dokterService.getDokterByUuid(dokter.getUuid()).getUsername()) &&
                        tagihan.getIsPaid() &&
                        tagihan.getTanggalTerbuat().getYear() == tahun &&
                        tagihan.getTanggalTerbuat().getMonthValue() == bulan &&
                        tagihan.getTanggalTerbuat().getDayOfMonth() == (i+1)){
                        incomePerDay += tagihan.getAppointment().getDokter().getTarif();
                    }
                }
                incomePerDayPerDokter.set(i, incomePerDay);
            }
            totalIncomeAllDokter.put(dokter.getNama(), incomePerDayPerDokter);
        }
        List<String> lstDokter = new ArrayList<>(totalIncomeAllDokter.keySet());
        List<List<Integer>> lstIncome = new ArrayList<>(totalIncomeAllDokter.values());

        if (lstIncome.size() < 5) {
            List<Integer> fillZero = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
            int size = lstIncome.size();
            for (var i = 0; i < 5 - size; i++) {
                lstIncome.add(fillZero);
            }
        }

        if (lstDokter.size() < 5) {
            var fillEmpty = "Not Selected";
            int size = lstDokter.size();
            for (var i = 0; i < 5 - size; i++) {
                lstDokter.add(fillEmpty);
            }
        }

        model.addAttribute("lstDokter", lstDokter);
        model.addAttribute("lstIncome", lstIncome);
        model.addAttribute(strTahun, tahun);
        model.addAttribute("bulan", bulan);

        return "line-chart-monthly";

    }

}