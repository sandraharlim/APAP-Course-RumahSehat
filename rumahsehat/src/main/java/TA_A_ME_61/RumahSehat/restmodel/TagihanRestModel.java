package TA_A_ME_61.RumahSehat.restmodel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagihanRestModel {
    @JsonProperty("kode")
    private String kode; // BILL-1

    @JsonProperty("tanggalTerbuat")
    private String tanggalTerbuat;

    @JsonProperty("tanggalBayar")
    private String tanggalBayar;
    
    @JsonProperty("isPaid")
    private Boolean isPaid;

    @JsonProperty("jumlahTagihan")
    private Long jumlahTagihan;
    
}
