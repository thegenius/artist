package com.lvonce.artist.transaction;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionInfo {

    private String name;
    private String taskUuid;
    private String params;

    private String executorUuid;
    private int preemptCount;
    private int status;

    private LocalDateTime expireTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
