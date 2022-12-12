package TA_A_ME_61.RumahSehat.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "pasien")
public class PasienModel extends UserModel implements Serializable {
    @NotNull
    @Column(name = "saldo", nullable = false, unique = false)
    private Long saldo;

    @NotNull
    @Column(name = "umur", nullable = false, unique = false)
    private Long umur;

    @OneToMany(mappedBy = "pasien", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentModel> listAppointment;

}
