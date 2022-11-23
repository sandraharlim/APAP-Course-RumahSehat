package TA_A_ME_61.RumahSehat.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmittedAppointment {
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;
}
