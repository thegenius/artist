package com.lvonce.artist.transaction;

public class TransactionPending extends Exception {
    public TransactionPending() {
        super();
    }

    public TransactionPending(String msg) {
        super(msg);
    }
}
