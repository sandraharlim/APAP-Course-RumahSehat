package TA_A_ME_61.RumahSehat.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "apoteker")
public class ApotekerModel  extends UserModel implements Serializable {

    @OneToMany(mappedBy = "apoteker",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ResepModel> listResep;
}
