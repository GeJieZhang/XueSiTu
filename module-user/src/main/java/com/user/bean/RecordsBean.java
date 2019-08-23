package com.user.bean;

import java.util.List;

public class RecordsBean {


    /**
     * code : 200
     * obj : {"total":3,"limit":10,"page":0,"rows":[{"amount":"+1.00","pay_type":1,"msg":"","create_date":"2019-08-23 11:08:54"},{"amount":"-10.00","pay_type":0,"msg":"提问消费10","create_date":"2019-08-23 11:07:41"},{"amount":"-0.00","pay_type":0,"msg":"够买一年级语文精品提升班","create_date":"2019-08-23 11:06:19"}]}
     * msg : 请求成功
     * seccess : true
     */

    private int code;
    private ObjBean obj;
    private String msg;
    private boolean seccess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public static class ObjBean {
        /**
         * total : 3
         * limit : 10
         * page : 0
         * rows : [{"amount":"+1.00","pay_type":1,"msg":"","create_date":"2019-08-23 11:08:54"},{"amount":"-10.00","pay_type":0,"msg":"提问消费10","create_date":"2019-08-23 11:07:41"},{"amount":"-0.00","pay_type":0,"msg":"够买一年级语文精品提升班","create_date":"2019-08-23 11:06:19"}]
         */

        private int total;
        private int limit;
        private int page;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * amount : +1.00
             * pay_type : 1
             * msg :
             * create_date : 2019-08-23 11:08:54
             */

            private String amount;
            private int pay_type;
            private String msg;
            private String create_date;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }
        }
    }
}
