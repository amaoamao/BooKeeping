package com.amaoamao.hsq.bookeeping.Entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;


/**
 * Created by mao on 17-2-27.
 */

public class Debt extends DataSupport {
    private long id;

    private String description;
    private Date timeCreated;
    private String type;
    @Column(defaultValue = "0.00")
    private Double amount;
    private Account payedBy;
    private Boolean isIn;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getPayedBy() {
        return payedBy;
    }

    public void setPayedBy(Account payedBy) {
        this.payedBy = payedBy;
    }

    public Boolean getIn() {
        return isIn;
    }

    public void setIn(Boolean in) {
        isIn = in;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
