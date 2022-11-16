package TA_A_ME_61.RumahSehat.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "admin")
public class AdminModel  extends UserModel implements Serializable {
}
