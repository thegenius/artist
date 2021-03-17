package com.lvonce.artist.transaction;

public class TransactionFail extends Exception {
    public TransactionFail() {
        super();
    }

    public TransactionFail(String msg) {
        super(msg);
    }
}
