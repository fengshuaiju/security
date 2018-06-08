package com.feng.accounts.application.command;

import lombok.Data;

import java.util.List;

@Data
public class AddMemberCommand {
    private String name;
    private String cellphone;
    private String email;
    private String password;
    private List<String> roleGroups;
}
