package TA_A_ME_61.RumahSehat.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "dokter")
public class DokterModel extends UserModel implements Serializable {
    @NotNull
    @Column(name = "tarif", nullable = false, unique = false)
    private Long tarif;

    @OneToMany(mappedBy = "dokter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentModel> listAppointment;
}
