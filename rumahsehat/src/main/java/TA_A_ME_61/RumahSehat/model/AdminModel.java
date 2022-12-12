package TA_A_ME_61.RumahSehat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "admin")
public class AdminModel  extends UserModel implements Serializable {
    @NotNull
    @Column(name = "is_Sso", nullable = false)
    private Boolean isSso;
}

