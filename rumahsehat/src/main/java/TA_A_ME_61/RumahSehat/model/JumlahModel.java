package TA_A_ME_61.RumahSehat.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "jumlah")
@Data
public class JumlahModel implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "kuantitas", nullable = false)
    private Integer kuantitas;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_obat", referencedColumnName = "id_obat", nullable = false) // 1 .. *
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ObatModel obat;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_resep", referencedColumnName = "id") // 0 .. *
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ResepModel resep;
}
