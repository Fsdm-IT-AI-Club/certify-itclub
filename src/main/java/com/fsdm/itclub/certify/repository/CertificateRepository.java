package com.fsdm.itclub.certify.repository;

import com.fsdm.itclub.certify.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
  Optional<Object> findByNumber(String uniqueNumber);
}