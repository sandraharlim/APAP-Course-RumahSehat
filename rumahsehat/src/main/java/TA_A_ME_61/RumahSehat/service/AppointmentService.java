package TA_A_ME_61.RumahSehat.service;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;

public interface AppointmentService {
    void addAppointment(AppointmentModel appointment);
    void setKodeNewAppointment(AppointmentModel appointment);
    void updateAppointment(AppointmentModel appointment);
    String validasi(AppointmentModel appointment);
    String getWaktuAwalWaktuAkhir(AppointmentModel appointment); // ex: (13.05-14.05)
}
