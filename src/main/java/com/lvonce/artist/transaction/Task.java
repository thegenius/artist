package com.lvonce.artist.transaction;

public interface Task<T, R> {
    enum State {

//    /** -------------- Saga Task -----------------------
//     * EMPTY ---> PENDING ---> SUCCESS ---> CANCELLING
//     *              |                           |
//     *              |                           |
//     *              | ------>   FAIL      <------
//     * -------------------------------------------------*/
//
//
//    /** -------------- Tcc Task ------------------------------
//     *                            ---> CONFIRMING ---> SUCCESS
//     *                           |
//     *                           |
//     * EMPTY ---> PENDING ---> LOCKED ---> CANCELLING
//     *              |                           |
//     *              |                           |
//     *              | ------>   FAIL      <------
//     * ------------------------------------------------------*/

        EMPTY,
        PENDING,
        SUCCESS,
        CANCELLING,
        FAIL,

        LOCKED,
        CONFIRMING,
    }

    R execute(T command);

    void cancel(T command);

    default void confirm(T command) { }
}
