package TA_A_ME_61.RumahSehat.controller;

import TA_A_ME_61.RumahSehat.model.ApotekerModel;
import TA_A_ME_61.RumahSehat.restmodel.ApotekerRestModel;
import TA_A_ME_61.RumahSehat.service.ApotekerService;
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
public class ApotekerController {
    @Qualifier("apotekerServiceImpl")
    @Autowired
    private ApotekerService apotekerService;

    @GetMapping("/apoteker/")
    public String listApoteker(Model model){
        List<ApotekerModel> listApoteker = apotekerService.getListApoteker();

        model.addAttribute("listApoteker",listApoteker);
        return "viewall-apoteker";
    }


    @GetMapping("/apoteker/add")
    public String addApotekerFormPage(Model model) {
        var apoteker = new ApotekerModel();

        model.addAttribute("apoteker", apoteker);

        return "form-add-apoteker";
    }

    @PostMapping(value = "/apoteker/add")
    public String addApotekerSubmitPage(@ModelAttribute ApotekerRestModel apoteker, BindingResult result,
                                        RedirectAttributes redirectAttrs) {
        var apotekerModel = new ApotekerModel();
        apotekerModel.setNama(apoteker.getNama());
        apotekerModel.setRole("Apoteker");
        apotekerModel.setUsername(apoteker.getUsername());
        apotekerModel.setEmail(apoteker.getEmail());
        apotekerModel.setPassword(apoteker.getPassword());
        apotekerService.addApoteker(apotekerModel);

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("error", "The error occurred.");
            return "redirect:/apoteker/add";
        }

        redirectAttrs.addFlashAttribute("success",
                String.format("Apoteker dengan nama %s berhasil ditambahkan ", apoteker.getNama()));

        return "redirect:/apoteker/";
    }

    @GetMapping("/apoteker/delete/{uuid}")
    public String deleteApoteker(@PathVariable String uuid, Model model){
        ApotekerModel apoteker = apotekerService.getApotekerByUuid(uuid);
        model.addAttribute("apoteker", apoteker);
        apotekerService.deleteApoteker(apoteker);
        return "delete-apoteker";

    }

}
