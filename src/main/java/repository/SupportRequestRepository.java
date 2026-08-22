package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.SupportRequest;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Long> {

}