package com.feng.accounts.support.persistence;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class ConcurrencySafeEntity extends IdentifiedDomainObject {

    private static final long serialVersionUID = -7730879258580252715L;

    @Version
    private long version;

}
