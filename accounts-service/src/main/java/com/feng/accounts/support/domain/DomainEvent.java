package com.feng.accounts.support.domain;

import lombok.Data;

@Data
public abstract class DomainEvent {

    private int version = 1;

}
