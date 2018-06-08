package com.feng.accounts.application.service;

import com.feng.accounts.model.Cellphone;

public interface SmsSender {
    void send(Cellphone cellphone, int templateId, String... args);
}
