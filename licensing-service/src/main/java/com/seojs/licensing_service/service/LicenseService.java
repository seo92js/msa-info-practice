package com.seojs.licensing_service.service;

import com.seojs.licensing_service.config.ServiceConfig;
import com.seojs.licensing_service.model.License;
import com.seojs.licensing_service.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LicenseService {
    private final MessageSource messages;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;

    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        if (license == null) {
            throw new IllegalArgumentException(
                    String.format(messages.getMessage(
                            "license.search.error.message", null, null),
                            licenseId, organizationId
                    ));
        }

        return license.withComment(config.getProperty());
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage("license.delete.message", null, null), licenseId);
        return responseMessage;
    }
}
