package com.olixie.animalshelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocketReceivedMessage {
    private Integer uid;
    private String content;
}
