package TA_A_ME_61.RumahSehat.restmodel;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResepDTO {

    private LocalDateTime tanggalWaktu;
    private String dokter;
    private String pasien;
    private String status;
    private String apoteker;
    private List<String> jumlah;

}
