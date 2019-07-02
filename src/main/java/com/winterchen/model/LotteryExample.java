package com.winterchen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LotteryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LotteryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIssueNoIsNull() {
            addCriterion("issue_no is null");
            return (Criteria) this;
        }

        public Criteria andIssueNoIsNotNull() {
            addCriterion("issue_no is not null");
            return (Criteria) this;
        }

        public Criteria andIssueNoEqualTo(String value) {
            addCriterion("issue_no =", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoNotEqualTo(String value) {
            addCriterion("issue_no <>", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoGreaterThan(String value) {
            addCriterion("issue_no >", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoGreaterThanOrEqualTo(String value) {
            addCriterion("issue_no >=", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoLessThan(String value) {
            addCriterion("issue_no <", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoLessThanOrEqualTo(String value) {
            addCriterion("issue_no <=", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoLike(String value) {
            addCriterion("issue_no like", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoNotLike(String value) {
            addCriterion("issue_no not like", value, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoIn(List<String> values) {
            addCriterion("issue_no in", values, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoNotIn(List<String> values) {
            addCriterion("issue_no not in", values, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoBetween(String value1, String value2) {
            addCriterion("issue_no between", value1, value2, "issueNo");
            return (Criteria) this;
        }

        public Criteria andIssueNoNotBetween(String value1, String value2) {
            addCriterion("issue_no not between", value1, value2, "issueNo");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenIsNull() {
            addCriterion("lottery_open is null");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenIsNotNull() {
            addCriterion("lottery_open is not null");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenEqualTo(String value) {
            addCriterion("lottery_open =", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenNotEqualTo(String value) {
            addCriterion("lottery_open <>", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenGreaterThan(String value) {
            addCriterion("lottery_open >", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenGreaterThanOrEqualTo(String value) {
            addCriterion("lottery_open >=", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenLessThan(String value) {
            addCriterion("lottery_open <", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenLessThanOrEqualTo(String value) {
            addCriterion("lottery_open <=", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenLike(String value) {
            addCriterion("lottery_open like", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenNotLike(String value) {
            addCriterion("lottery_open not like", value, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenIn(List<String> values) {
            addCriterion("lottery_open in", values, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenNotIn(List<String> values) {
            addCriterion("lottery_open not in", values, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenBetween(String value1, String value2) {
            addCriterion("lottery_open between", value1, value2, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andLotteryOpenNotBetween(String value1, String value2) {
            addCriterion("lottery_open not between", value1, value2, "lotteryOpen");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNull() {
            addCriterion("open_time is null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNotNull() {
            addCriterion("open_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeEqualTo(Date value) {
            addCriterion("open_time =", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotEqualTo(Date value) {
            addCriterion("open_time <>", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThan(Date value) {
            addCriterion("open_time >", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("open_time >=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThan(Date value) {
            addCriterion("open_time <", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThanOrEqualTo(Date value) {
            addCriterion("open_time <=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIn(List<Date> values) {
            addCriterion("open_time in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotIn(List<Date> values) {
            addCriterion("open_time not in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeBetween(Date value1, Date value2) {
            addCriterion("open_time between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotBetween(Date value1, Date value2) {
            addCriterion("open_time not between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andOddIsNull() {
            addCriterion("odd is null");
            return (Criteria) this;
        }

        public Criteria andOddIsNotNull() {
            addCriterion("odd is not null");
            return (Criteria) this;
        }

        public Criteria andOddEqualTo(String value) {
            addCriterion("odd =", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddNotEqualTo(String value) {
            addCriterion("odd <>", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddGreaterThan(String value) {
            addCriterion("odd >", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddGreaterThanOrEqualTo(String value) {
            addCriterion("odd >=", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddLessThan(String value) {
            addCriterion("odd <", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddLessThanOrEqualTo(String value) {
            addCriterion("odd <=", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddLike(String value) {
            addCriterion("odd like", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddNotLike(String value) {
            addCriterion("odd not like", value, "odd");
            return (Criteria) this;
        }

        public Criteria andOddIn(List<String> values) {
            addCriterion("odd in", values, "odd");
            return (Criteria) this;
        }

        public Criteria andOddNotIn(List<String> values) {
            addCriterion("odd not in", values, "odd");
            return (Criteria) this;
        }

        public Criteria andOddBetween(String value1, String value2) {
            addCriterion("odd between", value1, value2, "odd");
            return (Criteria) this;
        }

        public Criteria andOddNotBetween(String value1, String value2) {
            addCriterion("odd not between", value1, value2, "odd");
            return (Criteria) this;
        }

        public Criteria andBigIsNull() {
            addCriterion("big is null");
            return (Criteria) this;
        }

        public Criteria andBigIsNotNull() {
            addCriterion("big is not null");
            return (Criteria) this;
        }

        public Criteria andBigEqualTo(String value) {
            addCriterion("big =", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigNotEqualTo(String value) {
            addCriterion("big <>", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigGreaterThan(String value) {
            addCriterion("big >", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigGreaterThanOrEqualTo(String value) {
            addCriterion("big >=", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigLessThan(String value) {
            addCriterion("big <", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigLessThanOrEqualTo(String value) {
            addCriterion("big <=", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigLike(String value) {
            addCriterion("big like", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigNotLike(String value) {
            addCriterion("big not like", value, "big");
            return (Criteria) this;
        }

        public Criteria andBigIn(List<String> values) {
            addCriterion("big in", values, "big");
            return (Criteria) this;
        }

        public Criteria andBigNotIn(List<String> values) {
            addCriterion("big not in", values, "big");
            return (Criteria) this;
        }

        public Criteria andBigBetween(String value1, String value2) {
            addCriterion("big between", value1, value2, "big");
            return (Criteria) this;
        }

        public Criteria andBigNotBetween(String value1, String value2) {
            addCriterion("big not between", value1, value2, "big");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}