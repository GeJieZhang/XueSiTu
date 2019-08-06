package com.dayi.bean;

import java.util.List;

public class AskBean {


    /**
     * code : 200
     * obj : {"total":999965,"teaching_assistant":[{"id":15,"big_title":"小学数学","assistant_num":3,"price":5,"username":"唐老师","photo_url":"http://pvjdparam.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy"},{"id":28,"big_title":"一年级语文","assistant_num":1,"price":3,"username":"唐老师","photo_url":"http://pvjdparam.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy"},{"id":30,"big_title":"一年级语文","assistant_num":1,"price":3,"username":"唐老师","photo_url":"http://pvjdparam.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy"}]}
     * msg : 提问成功
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
         * total : 999965
         * teaching_assistant : [{"id":15,"big_title":"小学数学","assistant_num":3,"price":5,"username":"唐老师","photo_url":"http://pvjdparam.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy"},{"id":28,"big_title":"一年级语文","assistant_num":1,"price":3,"username":"唐老师","photo_url":"http://pvjdparam.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy"},{"id":30,"big_title":"一年级语文","assistant_num":1,"price":3,"username":"唐老师","photo_url":"http://pvjdparam.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy"}]
         */

        private int total;
        private List<TeachingAssistantBean> teaching_assistant;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<TeachingAssistantBean> getTeaching_assistant() {
            return teaching_assistant;
        }

        public void setTeaching_assistant(List<TeachingAssistantBean> teaching_assistant) {
            this.teaching_assistant = teaching_assistant;
        }

        public static class TeachingAssistantBean {
            /**
             * id : 15
             * big_title : 小学数学
             * assistant_num : 3
             * price : 5
             * username : 唐老师
             * photo_url : http://pvjdparam.bkt.clouddn.com/FscPYo_fSZ6zHz1ExL6ONLe9l1jy
             */

            private int id;
            private String big_title;
            private int assistant_num;
            private int price;
            private String username;
            private String photo_url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBig_title() {
                return big_title;
            }

            public void setBig_title(String big_title) {
                this.big_title = big_title;
            }

            public int getAssistant_num() {
                return assistant_num;
            }

            public void setAssistant_num(int assistant_num) {
                this.assistant_num = assistant_num;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPhoto_url() {
                return photo_url;
            }

            public void setPhoto_url(String photo_url) {
                this.photo_url = photo_url;
            }
        }
    }
}
