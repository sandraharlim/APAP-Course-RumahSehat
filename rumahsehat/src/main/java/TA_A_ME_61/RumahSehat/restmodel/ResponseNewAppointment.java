package TA_A_ME_61.RumahSehat.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseNewAppointment {
    @JsonProperty("success")
    private String success;

    @JsonProperty("error")
    private String error;
}
