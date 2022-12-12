package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentDb  extends JpaRepository<AppointmentModel, Long> {
    @Query("SELECT a FROM AppointmentModel a ORDER BY a.isDone")
    List<AppointmentModel> findAll();

    @Query("SELECT a FROM AppointmentModel a WHERE a.dokter.uuid = :uuid ORDER BY a.isDone")
    List<AppointmentModel> findAllByDokter(@Param("uuid") String uuid);

    @Query("SELECT a FROM AppointmentModel a WHERE a.pasien.uuid = :uuid ORDER BY a.isDone")
    List<AppointmentModel> findAllByPasien(@Param("uuid") String uuid);

    Optional<AppointmentModel> getAppointmentModelByKode(String kode);

    List<AppointmentModel> findAllByWaktuAwalBetween(LocalDateTime first, LocalDateTime last);

    List<AppointmentModel> findAllByDokter_UuidAndIsDoneAndTagihan_IsPaid(String uuid, boolean isDone, boolean isPaid);

    List<AppointmentModel> findAllByDokter_UsernameAndIsDoneAndTagihan_IsPaid(String username, boolean isDone, boolean isPaid);
}

