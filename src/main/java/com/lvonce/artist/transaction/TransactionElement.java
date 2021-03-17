package com.lvonce.artist.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionElement {
    String name;
    String taskUuid;
    String executorUuid;
    String params;
}
