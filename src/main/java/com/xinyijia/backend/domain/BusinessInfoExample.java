package com.xinyijia.backend.domain;

import java.util.ArrayList;
import java.util.List;

public class BusinessInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public BusinessInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNull() {
            addCriterion("business_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNotNull() {
            addCriterion("business_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameEqualTo(String value) {
            addCriterion("business_name =", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("business_name <>", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("business_name >", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_name >=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThan(String value) {
            addCriterion("business_name <", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("business_name <=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLike(String value) {
            addCriterion("business_name like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotLike(String value) {
            addCriterion("business_name not like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIn(List<String> values) {
            addCriterion("business_name in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("business_name not in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("business_name between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("business_name not between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessDescIsNull() {
            addCriterion("business_desc is null");
            return (Criteria) this;
        }

        public Criteria andBusinessDescIsNotNull() {
            addCriterion("business_desc is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessDescEqualTo(String value) {
            addCriterion("business_desc =", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotEqualTo(String value) {
            addCriterion("business_desc <>", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescGreaterThan(String value) {
            addCriterion("business_desc >", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescGreaterThanOrEqualTo(String value) {
            addCriterion("business_desc >=", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescLessThan(String value) {
            addCriterion("business_desc <", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescLessThanOrEqualTo(String value) {
            addCriterion("business_desc <=", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescLike(String value) {
            addCriterion("business_desc like", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotLike(String value) {
            addCriterion("business_desc not like", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescIn(List<String> values) {
            addCriterion("business_desc in", values, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotIn(List<String> values) {
            addCriterion("business_desc not in", values, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescBetween(String value1, String value2) {
            addCriterion("business_desc between", value1, value2, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotBetween(String value1, String value2) {
            addCriterion("business_desc not between", value1, value2, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerIsNull() {
            addCriterion("principal_owner is null");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerIsNotNull() {
            addCriterion("principal_owner is not null");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerEqualTo(String value) {
            addCriterion("principal_owner =", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerNotEqualTo(String value) {
            addCriterion("principal_owner <>", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerGreaterThan(String value) {
            addCriterion("principal_owner >", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("principal_owner >=", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerLessThan(String value) {
            addCriterion("principal_owner <", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerLessThanOrEqualTo(String value) {
            addCriterion("principal_owner <=", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerLike(String value) {
            addCriterion("principal_owner like", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerNotLike(String value) {
            addCriterion("principal_owner not like", value, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerIn(List<String> values) {
            addCriterion("principal_owner in", values, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerNotIn(List<String> values) {
            addCriterion("principal_owner not in", values, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerBetween(String value1, String value2) {
            addCriterion("principal_owner between", value1, value2, "principalOwner");
            return (Criteria) this;
        }

        public Criteria andPrincipalOwnerNotBetween(String value1, String value2) {
            addCriterion("principal_owner not between", value1, value2, "principalOwner");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table business_info
     *
     * @mbggenerated do_not_delete_during_merge Wed May 16 21:29:38 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table business_info
     *
     * @mbggenerated Wed May 16 21:29:38 CST 2018
     */
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