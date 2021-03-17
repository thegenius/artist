package com.lvonce.artist.transaction;

public enum  TransactionStatus {
    PENDING(0),
    SUCCESS(1),
    FAIL(2);

    private final int value;
    TransactionStatus(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
