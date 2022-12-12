package TA_A_ME_61.RumahSehat.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tagihan")
public class TagihanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode")
    private String kode;

    @NotNull
    @Column(name = "tanggalTerbuat", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalTerbuat;

    @Column(name = "tanggalBayar")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalBayar;

    @NotNull
    @Column(name = "isPaid", nullable = false)
    private Boolean isPaid;

    @NotNull
    @Column(name = "jumlahTagihan", nullable = false)
    private Long jumlahTagihan;

    @OneToOne(cascade = CascadeType.ALL) 
    private AppointmentModel appointment;

}


