package TA_A_ME_61.RumahSehat.restmodel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasienDTO {

    @JsonProperty("nama")
    private String nama;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("umur")
    private Long umur;

    @JsonProperty("saldo")
    private Long saldo;
}

