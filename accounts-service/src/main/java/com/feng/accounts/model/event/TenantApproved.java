package com.feng.accounts.model.event;

import com.feng.accounts.support.domain.DomainEvent;
import lombok.Builder;
import lombok.Data;

/**
 * Created by fengshuaiju on 2018-06-26.
 */
@Data
@Builder
public class TenantApproved extends DomainEvent {

    private String chineseName;
    private String code;

}
