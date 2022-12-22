package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import TA_A_ME_61.RumahSehat.model.DokterModel;
import TA_A_ME_61.RumahSehat.model.PasienModel;
import TA_A_ME_61.RumahSehat.model.TagihanModel;
import TA_A_ME_61.RumahSehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagihanServiceImpl implements TagihanService{

    @Autowired
    TagihanDb tagihanDb;

    @Autowired
    PasienService pasienService;

    @Override
    public List<TagihanModel> getListTagihan() {
        return tagihanDb.findAll();
    }

    @Override
    public void addTagihan(TagihanModel tagihan) {
        tagihanDb.save(tagihan);
    }

    @Override
    public TagihanModel getTagihanById(Long id) {
        return tagihanDb.findById(id).orElse(null);
    }
    
    public TagihanModel addTagihanByDokter(AppointmentModel appointment) {
        DokterModel dokter = appointment.getDokter();
        Long tarif = dokter.getTarif();
        TagihanModel tagihan = new TagihanModel();
        tagihan.setJumlahTagihan(tarif);
        tagihan.setAppointment(appointment);
        tagihan.setTanggalTerbuat(LocalDateTime.now());
        tagihan.setIsPaid(false);
        tagihanDb.save(tagihan);
        tagihan.setKode("BILL-" + tagihan.getId());
        tagihanDb.save(tagihan);
        return tagihan;
    }

    @Override
    public TagihanModel getTagihanByKode(String kode) {
        Optional<TagihanModel> tagihan = tagihanDb.findByKode(kode);
        if(tagihan.isPresent()){
            return tagihan.get();
        }
        return null;
    }

    @Override
    public Boolean bayarTagihan(TagihanModel tagihan, PasienModel pasien) {
        Long biaya = tagihan.getJumlahTagihan();
        Long saldo = pasien.getSaldo();

        if (biaya <= saldo) {
            pasien.setSaldo(saldo - biaya);
            pasienService.updatePasien(pasien);

            tagihan.setIsPaid(true);
            tagihan.setTanggalBayar(LocalDateTime.now());
            tagihanDb.save(tagihan); // update

            return true;
        }
        return false;
    }
}
