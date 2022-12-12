package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.ObatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObatDb extends JpaRepository<ObatModel, String> {

}