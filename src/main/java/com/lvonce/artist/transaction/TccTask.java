package com.lvonce.artist.transaction;

public interface TccTask<T, R> extends Task<T, R> {

    void cancel(T command);

    void confirm(T command);
}
