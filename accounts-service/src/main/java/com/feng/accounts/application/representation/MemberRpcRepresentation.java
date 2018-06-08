package com.feng.accounts.application.representation;

import lombok.Value;

@Value
public class MemberRpcRepresentation {
    private String name;
    private String cellphone;
    private String emailAddress;
}
