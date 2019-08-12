package com.user.bean;

import java.util.List;

public class ClassBean {


    /**
     * code : 200
     * obj : {"total":3,"limit":10,"page":0,"rows":[{"id":1,"name":"晚间陪伴课","type":0,"consume_class":0,"pay_status":0,"live_status":1,"live_room":[{"course_id":"14","course_name":"啦啦啦"},{"course_id":"17","course_name":"二年级数学"},{"course_id":"18","course_name":"三年级英语"},{"course_id":"19","course_name":"一年级语文"}],"pay_url":"http://192.168.0.105/index10.html?id=1&r=0"},{"id":8,"name":"","type":2,"consume_class":0,"pay_status":0,"live_status":0,"live_room":[],"pay_url":"http://192.168.0.105/index10.html?id=8&r=2"},{"id":9,"name":"测试1,测试2","type":2,"consume_class":0,"pay_status":0,"live_status":0,"live_room":[{"course_id":"11","course_name":"测试2"}],"pay_url":"http://192.168.0.105/index10.html?id=9&r=2"}]}
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
         * rows : [{"id":1,"name":"晚间陪伴课","type":0,"consume_class":0,"pay_status":0,"live_status":1,"live_room":[{"course_id":"14","course_name":"啦啦啦"},{"course_id":"17","course_name":"二年级数学"},{"course_id":"18","course_name":"三年级英语"},{"course_id":"19","course_name":"一年级语文"}],"pay_url":"http://192.168.0.105/index10.html?id=1&r=0"},{"id":8,"name":"","type":2,"consume_class":0,"pay_status":0,"live_status":0,"live_room":[],"pay_url":"http://192.168.0.105/index10.html?id=8&r=2"},{"id":9,"name":"测试1,测试2","type":2,"consume_class":0,"pay_status":0,"live_status":0,"live_room":[{"course_id":"11","course_name":"测试2"}],"pay_url":"http://192.168.0.105/index10.html?id=9&r=2"}]
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
             * id : 1
             * name : 晚间陪伴课
             * type : 0
             * consume_class : 0
             * pay_status : 0
             * live_status : 1
             * live_room : [{"course_id":"14","course_name":"啦啦啦"},{"course_id":"17","course_name":"二年级数学"},{"course_id":"18","course_name":"三年级英语"},{"course_id":"19","course_name":"一年级语文"}]
             * pay_url : http://192.168.0.105/index10.html?id=1&r=0
             */

            private int id;
            private String name;
            private int type;
            private String consume_class;
            private int pay_status;
            private int live_status;
            private String pay_url;
            private List<LiveRoomBean> live_room;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getConsume_class() {
                return consume_class;
            }

            public void setConsume_class(String consume_class) {
                this.consume_class = consume_class;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public int getLive_status() {
                return live_status;
            }

            public void setLive_status(int live_status) {
                this.live_status = live_status;
            }

            public String getPay_url() {
                return pay_url;
            }

            public void setPay_url(String pay_url) {
                this.pay_url = pay_url;
            }

            public List<LiveRoomBean> getLive_room() {
                return live_room;
            }

            public void setLive_room(List<LiveRoomBean> live_room) {
                this.live_room = live_room;
            }

            public static class LiveRoomBean {
                /**
                 * course_id : 14
                 * course_name : 啦啦啦
                 */

                private String course_id;
                private String course_name;

                private String status;

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getCourse_id() {
                    return course_id;
                }

                public void setCourse_id(String course_id) {
                    this.course_id = course_id;
                }

                public String getCourse_name() {
                    return course_name;
                }

                public void setCourse_name(String course_name) {
                    this.course_name = course_name;
                }
            }
        }
    }
}
