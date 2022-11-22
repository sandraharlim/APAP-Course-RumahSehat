package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.repository.JumlahDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class JumlahServiceImpl implements JumlahService{
    @Autowired
    JumlahDb jumlahDb;
}
