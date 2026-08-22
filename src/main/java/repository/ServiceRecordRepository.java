package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.ServiceRecord;

public interface ServiceRecordRepository
        extends JpaRepository<ServiceRecord, Long> {

    List<ServiceRecord> findByCustomerId(Long customerId);

    List<ServiceRecord> findByVehicleNumber(String vehicleNumber);

    List<ServiceRecord> findByStatus(String status);

    Optional<ServiceRecord> findTopByVehicleNumberOrderByIdDesc(
            String vehicleNumber);
}