package com.dayi.bean;

import java.util.List;

public class QuestionList {


    /**
     * code : 200
     * obj : {"history_page":{"total":1,"limit":10,"page":0,"rows":[{"remaining":0,"file":["http://pu00k0ssj.bkt.clouddn.com/FiNYrIEomd96IiTL_xwS7dH8q97T"],"question_id":31}]},"question_list":[{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/null"],"question_id":7},{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/Frr3N-lgxgc7SmG06a4wvjWx2zgy"],"question_id":8},{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/FtpfWpt3lp6KWp6JMcfM56zyFObZ"],"question_id":9},{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/FtpfWpt3lp6KWp6JMcfM56zyFObZ"],"question_id":10},{"remaining":10,"file":["http://pu00k0ssj.bkt.clouddn.com/Fh74dLK2ZhuJKbiZ5n-vsMJzPlX3"],"question_id":37}]}
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
         * history_page : {"total":1,"limit":10,"page":0,"rows":[{"remaining":0,"file":["http://pu00k0ssj.bkt.clouddn.com/FiNYrIEomd96IiTL_xwS7dH8q97T"],"question_id":31}]}
         * question_list : [{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/null"],"question_id":7},{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/Frr3N-lgxgc7SmG06a4wvjWx2zgy"],"question_id":8},{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/FtpfWpt3lp6KWp6JMcfM56zyFObZ"],"question_id":9},{"remaining":5,"file":["http://pu00k0ssj.bkt.clouddn.com/FtpfWpt3lp6KWp6JMcfM56zyFObZ"],"question_id":10},{"remaining":10,"file":["http://pu00k0ssj.bkt.clouddn.com/Fh74dLK2ZhuJKbiZ5n-vsMJzPlX3"],"question_id":37}]
         */

        private HistoryPageBean history_page;
        private List<QuestionListBean> question_list;
        private String visit_rule_link;

        public String getVisit_rule_link() {
            return visit_rule_link;
        }

        public void setVisit_rule_link(String visit_rule_link) {
            this.visit_rule_link = visit_rule_link;
        }

        public HistoryPageBean getHistory_page() {
            return history_page;
        }

        public void setHistory_page(HistoryPageBean history_page) {
            this.history_page = history_page;
        }

        public List<QuestionListBean> getQuestion_list() {
            return question_list;
        }

        public void setQuestion_list(List<QuestionListBean> question_list) {
            this.question_list = question_list;
        }

        public static class HistoryPageBean {
            /**
             * total : 1
             * limit : 10
             * page : 0
             * rows : [{"remaining":0,"file":["http://pu00k0ssj.bkt.clouddn.com/FiNYrIEomd96IiTL_xwS7dH8q97T"],"question_id":31}]
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
                 * remaining : 0
                 * file : ["http://pu00k0ssj.bkt.clouddn.com/FiNYrIEomd96IiTL_xwS7dH8q97T"]
                 * question_id : 31
                 */

                private int remaining;
                private int question_id;
                private List<String> file;

                public int getRemaining() {
                    return remaining;
                }

                public void setRemaining(int remaining) {
                    this.remaining = remaining;
                }

                public int getQuestion_id() {
                    return question_id;
                }

                public void setQuestion_id(int question_id) {
                    this.question_id = question_id;
                }

                public List<String> getFile() {
                    return file;
                }

                public void setFile(List<String> file) {
                    this.file = file;
                }
            }
        }

        public static class QuestionListBean {
            /**
             * remaining : 5
             * file : ["http://pu00k0ssj.bkt.clouddn.com/null"]
             * question_id : 7
             */

            private int remaining;
            private int question_id;
            private List<String> file;

            public int getRemaining() {
                return remaining;
            }

            public void setRemaining(int remaining) {
                this.remaining = remaining;
            }

            public int getQuestion_id() {
                return question_id;
            }

            public void setQuestion_id(int question_id) {
                this.question_id = question_id;
            }

            public List<String> getFile() {
                return file;
            }

            public void setFile(List<String> file) {
                this.file = file;
            }
        }
    }
}
