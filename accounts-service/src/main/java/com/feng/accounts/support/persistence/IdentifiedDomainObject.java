package com.feng.accounts.support.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class IdentifiedDomainObject implements Serializable {

    private static final long serialVersionUID = -4363705785951187718L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    protected long id() {
        return this.id;
    }

}
