package com.lvonce.artist.example.dal.entity;

import lombok.Data;

import java.time.LocalDateTime;

//          "task_uuid VARCHAR(128) NOT NULL, \n" +
//                  "params TEXT NOT NULL, \n" +
//                  "status TINYINT NOT NULL,\n"+
//
//                  "executor_uuid VARCHAR(128) NOT NULL, \n"+
//                  "expire_time DATETIME NOT NULL, \n"+
//                  "create_time DATETIME NOT NULL, \n"+
//                  "update_time DATETIME NOT NULL, \n"+
//                  "preempt_count INT NOT NULL DEFAULT 0, \n"+

@Data
public class TaskInfo {

    private String taskUuid;
    private String params;

    private String executorUuid;
    private int preemptCount;
    private int status;
    
    private LocalDateTime expireTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
