package TA_A_ME_61.RumahSehat.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JumlahRestModel {
    @JsonProperty("namaObat")
    private String namaObat; // APT-id

    @JsonProperty("kuantitas")
    private String kuantitas; // nama dokter
}
