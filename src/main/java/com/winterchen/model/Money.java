package com.winterchen.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "money")
public class Money {
    @Id
    private Integer id;

    private Double money;

    private String bet;

    private String result;

    private Double amount;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return money
     */
    public Double getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * @return bet
     */
    public String getBet() {
        return bet;
    }

    /**
     * @param bet
     */
    public void setBet(String bet) {
        this.bet = bet == null ? null : bet.trim();
    }

    /**
     * @return result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * @return amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}