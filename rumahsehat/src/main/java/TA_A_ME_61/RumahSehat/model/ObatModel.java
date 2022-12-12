package TA_A_ME_61.RumahSehat.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "obat")
public class ObatModel implements Serializable {
    @Id
    @Size(max = 255)
    @Column(name = "id_obat")
    private String idObat;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_obat", nullable = false)
    private String namaObat;

    @NotNull
    @Column(name = "stok", nullable = false, columnDefinition = "integer default 100")
    private Integer stok;

    @NotNull
    @Column(name = "harga", nullable = false)
    private Integer harga;

    @OneToMany(mappedBy = "obat",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JumlahModel> listJumlah;

}