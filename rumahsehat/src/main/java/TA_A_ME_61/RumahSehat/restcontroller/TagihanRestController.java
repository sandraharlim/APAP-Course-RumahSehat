package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.restmodel.AppointmentRestModel;
import TA_A_ME_61.RumahSehat.restservice.AppointmentRestService;
import TA_A_ME_61.RumahSehat.service.AppointmentService;
import TA_A_ME_61.RumahSehat.service.DokterService;
import TA_A_ME_61.RumahSehat.service.PasienService;
import TA_A_ME_61.RumahSehat.service.TagihanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pasien")
public class TagihanRestController {
    @Qualifier("tagihanServiceImpl")
    @Autowired
    private TagihanServiceImpl tagihanService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRestService appointmentRestService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private PasienService pasienService;


    @GetMapping(value = "/tagihan")
    private List<TagihanModel> listTagihan() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        PasienModel pasien = pasienService.getPasienByUsername(username);

        List<AppointmentModel> listAppointment = new ArrayList<>();
        List<TagihanModel> listTagihan = new ArrayList<>();

//        List<AppointmentRestModel> listRestAppt = new ArrayList<>();

        if (pasien != null) {
            listAppointment = appointmentService.getAllApptByPasien(pasien);
            listAppointment = (listAppointment == null) ? new ArrayList<>() : listAppointment; // kalau null diisi list kosong aja
//            listRestAppt = appointmentRestService.convertApptToRestAppt(listAppointment);

        }
        for (AppointmentModel appointment : listAppointment){
            if (appointment.getTagihan()!= null){
                listTagihan.add(appointment.getTagihan());
            }
        }

        return listTagihan;
    }

//    @PostMapping(value = "/add")
//    private TagihanModel addTagihan(@Valid @RequestBody TagihanModel tagihan, BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors()) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field.");
//        } else {
//            return tagihanService.addTagihan(tagihan);
//        }
//    }

}




