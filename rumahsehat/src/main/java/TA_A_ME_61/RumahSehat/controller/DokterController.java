package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.restmodel.DokterRestModel;
import TA_A_ME_61.RumahSehat.service.DokterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DokterController {
    @Qualifier("dokterServiceImpl")
    @Autowired
    private DokterService dokterService;

    @GetMapping("/dokter/")
    public String listDokter(Model model){
        List<DokterModel> listDokter = dokterService.getListDokter();

        model.addAttribute("listDokter",listDokter);
        return "viewall-dokter";
    }

    @GetMapping("/dokter/add")
    public String addDokterFormPage(Model model) {
        var dokter = new DokterModel();

        model.addAttribute("dokter", dokter);

        return "form-add-dokter";
    }

    @PostMapping(value = "/dokter/add")
    public String addDokterSubmitPage(@ModelAttribute DokterRestModel dokter, BindingResult result,
                                      RedirectAttributes redirectAttrs) {
        var dokterModel = new DokterModel();
        dokterModel.setNama(dokter.getNama());
        dokterModel.setRole("Dokter");
        dokterModel.setUsername(dokter.getUsername());
        dokterModel.setEmail(dokter.getEmail());
        dokterModel.setPassword(dokter.getPassword());
        dokterModel.setTarif(dokter.getTarif());
        dokterService.addDokter(dokterModel);
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/dokter/add";
        }

        redirectAttrs.addFlashAttribute("success",
                String.format("Dokter dengan nama %s berhasil ditambahkan ", dokter.getNama()));

        return "redirect:/dokter/";
    }

    @GetMapping("/dokter/update/{uuid}")
    public String updateDokterFormPage(@PathVariable String uuid, Model model){
        DokterModel dokter = dokterService.getDokterByUuid(uuid);
        model.addAttribute("updateDokter", dokter);

        return "form-update-dokter";
    }

    @PostMapping("/dokter/update/{uuid}")
    public String updateDokterSubmitPage(@ModelAttribute DokterRestModel dokter, BindingResult result,
                                         RedirectAttributes redirectAttrs){
        var dokterModel = new DokterModel();
        dokterModel.setUuid(dokter.getUuid());
        dokterModel.setNama(dokter.getNama());
        dokterModel.setRole("Dokter");
        dokterModel.setUsername(dokter.getUsername());
        dokterModel.setEmail(dokter.getEmail());
        dokterModel.setPassword(dokter.getPassword());
        dokterModel.setTarif(dokter.getTarif());
        dokterService.updateDokter(dokterModel);
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/dokter/update";
        }

        redirectAttrs.addFlashAttribute("success",
                String.format("Dokter dengan nama %s berhasil diubah ", dokter.getNama() ));

        return "redirect:/dokter/";
    }

    @GetMapping("/dokter/delete/{uuid}")
    public String deleteDokter(@PathVariable String uuid, Model model){
        DokterModel dokter = dokterService.getDokterByUuid(uuid);
        model.addAttribute("deleteDokter", dokter);
        dokterService.deleteDokter(dokter);
        return "delete-dokter";

    }

    @GetMapping("/dokter/chart/lineBulanan")
    public String linePendapatan(Model model) {
        List<DokterModel> listDokter = dokterService.getListDokter();
        model.addAttribute("listDokter", listDokter);
        return "line-pendapatan-bulanan";
    }
}
