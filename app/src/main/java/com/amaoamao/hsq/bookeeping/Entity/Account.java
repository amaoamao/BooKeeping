package com.amaoamao.hsq.bookeeping.Entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by mao on 17-2-27.
 */

public class Account extends DataSupport {

    @Column(unique = true)
    private String name;
    private Double balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return name;
    }
}
