package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    ChartService chartService;

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

}