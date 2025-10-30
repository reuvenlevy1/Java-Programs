package com.reuven.healthcare.repository;

import com.reuven.healthcare.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // JpaRepository inherently provides:
    // - save()        : insert or update an appointment
    // - findById()    : get a patient by ID
    // - findAll()     : list all patients
    // - deleteById()  : remove a patient by ID
	
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
           "AND ((a.startTime <= :endTime AND a.endTime >= :startTime))")
    List<Appointment> findOverlappingAppointmentsForDoctor(
            @Param("doctorId") Long doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId " +
           "AND ((a.startTime <= :endTime AND a.endTime >= :startTime))")
    List<Appointment> findOverlappingAppointmentsForPatient(
            @Param("patientId") Long patientId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}