package TA_A_ME_61.RumahSehat.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResepRestModel {
    @JsonProperty("id")
    private String id;

    @JsonProperty("tanggalWaktu")
    private String tanggalWaktu;

    @JsonProperty("dokter")
    private String dokter;

    @JsonProperty("pasien")
    private String pasien;

    @JsonProperty("status")
    private String status;

    @JsonProperty("apoteker")
    private String apoteker;

    @JsonProperty("jumlah")
    private List<JumlahRestModel> jumlah;

}
