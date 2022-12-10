package TA_A_ME_61.RumahSehat.restcontroller;

import TA_A_ME_61.RumahSehat.model.ResepModel;
import TA_A_ME_61.RumahSehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/resep")
public class ResepRestController {
    @Autowired
    private ResepService resepService;

    @GetMapping("/detail/{id}")
    public ResepModel viewDetailResep(@PathVariable("id") Long id){
        try {
            return resepService.getResepById(id);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Resep " + id + " not found");
        }
    }
}
