package TA_A_ME_61.RumahSehat.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "resep")
@Data
public class ResepModel implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_done", nullable = false)
    private Boolean isDone; // initial value nya false

    @NotNull
    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "resep", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JumlahModel> listJumlah;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private AppointmentModel appointment;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "uuid_apoteker", referencedColumnName = "uuid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ApotekerModel apoteker;
}
