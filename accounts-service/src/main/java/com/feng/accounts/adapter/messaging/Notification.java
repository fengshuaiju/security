package com.feng.accounts.adapter.messaging;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Created by fengshuaiju on 2018-06-12.
 */
@Data
@Builder
@ToString
public class Notification {
    private String typeName;
    private String content;
}
