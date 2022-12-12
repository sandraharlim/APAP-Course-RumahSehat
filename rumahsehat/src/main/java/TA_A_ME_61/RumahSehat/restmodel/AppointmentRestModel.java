package TA_A_ME_61.RumahSehat.restmodel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRestModel {

    @JsonProperty("kode")
    private String kode; // APT-id

    @JsonProperty("dokter")
    private String dokter; // nama dokter

    @JsonProperty("pasien")
    private String pasien; // nama pasien

    @JsonProperty("waktuAwal")
    private String waktuAwal; // 'dd MMMM yyyy HH:mm'
    
    @JsonProperty("status")
    private String status; // 'Sudah Selesai' or 'Belum Dimulai'

    @JsonProperty("resepId")
    private String resepId;

}
