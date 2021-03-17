package com.lvonce.artist.example.dal.entity;

//   "scope       VARCHAR(32)  NOT NULL, \n" +
//           "account_id  VARCHAR(64)  NOT NULL, \n" +
//           "resource_id VARCHAR(128) NOT NULL, \n"+
//
//           "status TINYINT NOT NULL,\n"+
//           "value        DECIMAL(65, 30) NOT NULL DEFAULT 0.0, \n"+
//           "origin_value DECIMAL(65, 30) NOT NULL DEFAULT 0.0, \n"+
//
//           "create_time DATETIME NOT NULL, \n"+
//           "update_time DATETIME NOT NULL, \n"+

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceInfo {

    private String scope;
    private String accountId;
    private String resourceId;

    private BigDecimal value;
    private BigDecimal origin_value;
    private int status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
