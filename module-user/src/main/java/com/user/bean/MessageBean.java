package com.user.bean;

import java.util.List;

public class MessageBean {


    /**
     * code : 200
     * obj : {"association_msg":true,"official_data":[{"id":10,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0},{"id":9,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0},{"id":8,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0},{"id":4,"title":"版本更新","content":"有新版本上线了，赶快更新。","publish_time":"2019-08-06 ","read_status":0},{"id":3,"title":"最新活动","content":"买一得一，快来抢购","publish_time":"2019-08-06 ","read_status":0},{"id":2,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0}],"comment_msg":true,"order_msg":true,"official_msg":true}
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
         * association_msg : true
         * official_data : [{"id":10,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0},{"id":9,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0},{"id":8,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0},{"id":4,"title":"版本更新","content":"有新版本上线了，赶快更新。","publish_time":"2019-08-06 ","read_status":0},{"id":3,"title":"最新活动","content":"买一得一，快来抢购","publish_time":"2019-08-06 ","read_status":0},{"id":2,"title":"热门推送","content":"今天是个好日子","publish_time":"2019-08-06 ","read_status":0}]
         * comment_msg : true
         * order_msg : true
         * official_msg : true
         */

        private boolean association_msg;
        private boolean comment_msg;
        private boolean order_msg;
        private boolean official_msg;
        private List<OfficialDataBean> official_data;

        public boolean isAssociation_msg() {
            return association_msg;
        }

        public void setAssociation_msg(boolean association_msg) {
            this.association_msg = association_msg;
        }

        public boolean isComment_msg() {
            return comment_msg;
        }

        public void setComment_msg(boolean comment_msg) {
            this.comment_msg = comment_msg;
        }

        public boolean isOrder_msg() {
            return order_msg;
        }

        public void setOrder_msg(boolean order_msg) {
            this.order_msg = order_msg;
        }

        public boolean isOfficial_msg() {
            return official_msg;
        }

        public void setOfficial_msg(boolean official_msg) {
            this.official_msg = official_msg;
        }

        public List<OfficialDataBean> getOfficial_data() {
            return official_data;
        }

        public void setOfficial_data(List<OfficialDataBean> official_data) {
            this.official_data = official_data;
        }

        public static class OfficialDataBean {
            /**
             * id : 10
             * title : 热门推送
             * content : 今天是个好日子
             * publish_time : 2019-08-06
             * read_status : 0
             */

            private int id;
            private String title;
            private String content;
            private String publish_time;
            private int read_status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public int getRead_status() {
                return read_status;
            }

            public void setRead_status(int read_status) {
                this.read_status = read_status;
            }
        }
    }
}
