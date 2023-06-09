package TA_A_ME_61.RumahSehat.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "appointment")
public class AppointmentModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode")
    private String kode;

    @NotNull
    @Column(name = "waktuAwal", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuAwal;

    @NotNull
    @Column(name = "isDone", nullable = false)
    private Boolean isDone; // initial value nya false

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid_dokter", referencedColumnName = "uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DokterModel dokter;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid_pasien", referencedColumnName = "uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PasienModel pasien;

    @OneToOne(cascade = CascadeType.ALL) 
    private TagihanModel tagihan;

    @OneToOne(cascade = CascadeType.ALL) 
    private ResepModel resep;
}
