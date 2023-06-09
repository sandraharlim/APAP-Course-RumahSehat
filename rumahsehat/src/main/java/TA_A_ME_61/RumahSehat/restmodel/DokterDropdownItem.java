package TA_A_ME_61.RumahSehat.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DokterDropdownItem {
    @JsonProperty("nama-tarif")
    private String namaTarif;

    @JsonProperty("uuid")
    private String uuid;
}
