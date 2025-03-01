package com.fsdm.itclub.certify.security;


import com.fsdm.itclub.certify.entity.Certificate;
import com.fsdm.itclub.certify.repository.CertificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class CertificateService {
    private static final Logger logger = LoggerFactory.getLogger(CertificateService.class);
    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Transactional
    public Certificate createCertificate(Certificate certificate) {
        try {
            certificate.setNumber(generateUniqueNumber());
            return certificateRepository.save(certificate);
        } catch (DataIntegrityViolationException e) {
            logger.error("Duplicate certificate number", e);
            throw new RuntimeException("Duplicate certificate number", e);
        }
    }

    private String generateUniqueNumber() {
        String prefix = "FSDMITCLUB";
        String suffix = "CD";
        Random random = new SecureRandom();
        String uniqueNumber;
        do {
            uniqueNumber = prefix + random.nextInt(1_000_000_000) + suffix;
        } while (certificateRepository.findByNumber(uniqueNumber).isPresent());
        logger.info("Generated unique number: {}", uniqueNumber);
        return uniqueNumber;
    }
}
