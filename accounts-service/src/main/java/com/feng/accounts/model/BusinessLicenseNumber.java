package com.feng.accounts.model;

import com.feng.accounts.support.utils.Validate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Embeddable
@Value
@Accessors(fluent = true)
public class BusinessLicenseNumber {

    private String businessLicenseNumber;

    public BusinessLicenseNumber(String businessLicenseNumber) {
        Validate.matchesPattern(businessLicenseNumber, "^([a-zA-Z0-9]{15}|[a-zA-Z0-9]{18})$",
                "error.tenant.business-license-no.format.invalid");

        this.businessLicenseNumber = businessLicenseNumber;
    }

}
