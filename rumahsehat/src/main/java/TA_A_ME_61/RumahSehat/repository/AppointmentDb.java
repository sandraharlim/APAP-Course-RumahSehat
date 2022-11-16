package TA_A_ME_61.RumahSehat.repository;

import TA_A_ME_61.RumahSehat.model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentDb  extends JpaRepository<AppointmentModel, Long> {
//    Optional<CourseModel> findByCode(String code);
//
//    @Query("SELECT c FROM CourseModel c WHERE c.code = :code")
//    Optional<CourseModel> findByCodeUsingQuery(@Param("code") String code);
//
//    @Query("SELECT c FROM CourseModel c ORDER BY c.nameCourse")
//    List<CourseModel> findAllSorted();

    @Query("SELECT a FROM AppointmentModel a WHERE a.dokter.uuid = :uuid")
    List<AppointmentModel> findAllByDokter(@Param("uuid") String uuid);
}
