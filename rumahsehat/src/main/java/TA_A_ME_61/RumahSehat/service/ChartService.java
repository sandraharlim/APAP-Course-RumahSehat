package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;

import java.util.List;

public interface ChartService {
    Long[] getIncome(List<AppointmentModel> listAppointment);
}
