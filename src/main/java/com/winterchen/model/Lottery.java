package com.winterchen.model;

import com.winterchen.ExcelField;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lottery")
public class Lottery {
    @Id
    @Column(name = "issue_no")
    @ExcelField(name = "期号")
    private String issueNo;

    @ExcelField(name = "开奖号码")
    @Column(name = "lottery_open")
    private String lotteryOpen;

    @Column(name = "open_time")
    private Date openTime;

    private String odd;

    private String big;

    /**
     * @return issue_no
     */
    public String getIssueNo() {
        return issueNo;
    }

    /**
     * @param issueNo
     */
    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo == null ? null : issueNo.trim();
    }

    /**
     * @return lottery_open
     */
    public String getLotteryOpen() {
        return lotteryOpen;
    }

    /**
     * @param lotteryOpen
     */
    public void setLotteryOpen(String lotteryOpen) {
        this.lotteryOpen = lotteryOpen == null ? null : lotteryOpen.trim();
    }

    /**
     * @return open_time
     */
    public Date getOpenTime() {
        return openTime;
    }

    /**
     * @param openTime
     */
    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    /**
     * @return odd
     */
    public String getOdd() {
        return odd;
    }

    /**
     * @param odd
     */
    public void setOdd(String odd) {
        this.odd = odd == null ? null : odd.trim();
    }

    /**
     * @return big
     */
    public String getBig() {
        return big;
    }

    /**
     * @param big
     */
    public void setBig(String big) {
        this.big = big == null ? null : big.trim();
    }
}