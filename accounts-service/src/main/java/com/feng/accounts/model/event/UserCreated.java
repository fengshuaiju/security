package com.feng.accounts.model.event;

import com.feng.accounts.support.domain.DomainEvent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreated extends DomainEvent{

    private String userName;
    private String nickName;

}
