package com.lib.bean;

public class PushBean {


    /**
     * a : {"nameValuePairs":{"display_type":"notification","extra":{"nameValuePairs":{"xuesitu_data":"{\"obj\":{\"subject_name\":\"语文\",\"expected_value\":5.00,\"question_id\":101,\"grade_name\":\"四年级\"},\"type\":1}"}},"msg_id":"uufevxl156498746556101","body":{"nameValuePairs":{"after_open":"go_activity","ticker":"{\"data\":\"{\\\"obj\\\":{\\\"subject_name\\\":\\\"语文\\\",\\\"expected_value\\\":5.00,\\\"question_id\\\":101,\\\"grade_name\\\":\\\"四年级\\\"},\\\"type\\\":1}\",\"title\":\"有条新的消息\",\"content\":\"推送内容\"}","activity":"com.dayi.activity.StudentQuestionDetailActivity","text":"推送内容","title":"有条新的消息"}},"random_min":0}}
     * activity : com.dayi.activity.StudentQuestionDetailActivity
     * after_open : go_activity
     * alias :
     * bar_image :
     * builder_id : 0
     * clickOrDismiss : false
     * custom :
     * display_type : notification
     * expand_image :
     * extra : {"xuesitu_data":"{\"obj\":{\"subject_name\":\"语文\",\"expected_value\":5.00,\"question_id\":101,\"grade_name\":\"四年级\"},\"type\":1}"}
     * icon :
     * img :
     * isAction : false
     * largeIcon :
     * message_id : f__-k596YIaSfwEA&&uufevxl156498746556101&&W1HnGWgMcZQDAEIGjRUOIfme&&00&&
     * msg_id : uufevxl156498746556101
     * play_lights : true
     * play_sound : true
     * play_vibrate : true
     * pulledWho :
     * pulled_package :
     * pulled_service :
     * random_min : 0
     * recall :
     * screen_on : false
     * sound :
     * text : 推送内容
     * ticker : {"data":"{\"obj\":{\"subject_name\":\"语文\",\"expected_value\":5.00,\"question_id\":101,\"grade_name\":\"四年级\"},\"type\":1}","title":"有条新的消息","content":"推送内容"}
     * title : 有条新的消息
     * url :
     */

    private ABean a;
    private String activity;
    private String after_open;
    private String alias;
    private String bar_image;
    private int builder_id;
    private boolean clickOrDismiss;
    private String custom;
    private String display_type;
    private String expand_image;
    private ExtraBeanX extra;
    private String icon;
    private String img;
    private boolean isAction;
    private String largeIcon;
    private String message_id;
    private String msg_id;
    private boolean play_lights;
    private boolean play_sound;
    private boolean play_vibrate;
    private String pulledWho;
    private String pulled_package;
    private String pulled_service;
    private int random_min;
    private String recall;
    private boolean screen_on;
    private String sound;
    private String text;
    private String ticker;
    private String title;
    private String url;

    public ABean getA() {
        return a;
    }

    public void setA(ABean a) {
        this.a = a;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAfter_open() {
        return after_open;
    }

    public void setAfter_open(String after_open) {
        this.after_open = after_open;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBar_image() {
        return bar_image;
    }

    public void setBar_image(String bar_image) {
        this.bar_image = bar_image;
    }

    public int getBuilder_id() {
        return builder_id;
    }

    public void setBuilder_id(int builder_id) {
        this.builder_id = builder_id;
    }

    public boolean isClickOrDismiss() {
        return clickOrDismiss;
    }

    public void setClickOrDismiss(boolean clickOrDismiss) {
        this.clickOrDismiss = clickOrDismiss;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public String getExpand_image() {
        return expand_image;
    }

    public void setExpand_image(String expand_image) {
        this.expand_image = expand_image;
    }

    public ExtraBeanX getExtra() {
        return extra;
    }

    public void setExtra(ExtraBeanX extra) {
        this.extra = extra;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isIsAction() {
        return isAction;
    }

    public void setIsAction(boolean isAction) {
        this.isAction = isAction;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public boolean isPlay_lights() {
        return play_lights;
    }

    public void setPlay_lights(boolean play_lights) {
        this.play_lights = play_lights;
    }

    public boolean isPlay_sound() {
        return play_sound;
    }

    public void setPlay_sound(boolean play_sound) {
        this.play_sound = play_sound;
    }

    public boolean isPlay_vibrate() {
        return play_vibrate;
    }

    public void setPlay_vibrate(boolean play_vibrate) {
        this.play_vibrate = play_vibrate;
    }

    public String getPulledWho() {
        return pulledWho;
    }

    public void setPulledWho(String pulledWho) {
        this.pulledWho = pulledWho;
    }

    public String getPulled_package() {
        return pulled_package;
    }

    public void setPulled_package(String pulled_package) {
        this.pulled_package = pulled_package;
    }

    public String getPulled_service() {
        return pulled_service;
    }

    public void setPulled_service(String pulled_service) {
        this.pulled_service = pulled_service;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }

    public boolean isScreen_on() {
        return screen_on;
    }

    public void setScreen_on(boolean screen_on) {
        this.screen_on = screen_on;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ABean {
        /**
         * nameValuePairs : {"display_type":"notification","extra":{"nameValuePairs":{"xuesitu_data":"{\"obj\":{\"subject_name\":\"语文\",\"expected_value\":5.00,\"question_id\":101,\"grade_name\":\"四年级\"},\"type\":1}"}},"msg_id":"uufevxl156498746556101","body":{"nameValuePairs":{"after_open":"go_activity","ticker":"{\"data\":\"{\\\"obj\\\":{\\\"subject_name\\\":\\\"语文\\\",\\\"expected_value\\\":5.00,\\\"question_id\\\":101,\\\"grade_name\\\":\\\"四年级\\\"},\\\"type\\\":1}\",\"title\":\"有条新的消息\",\"content\":\"推送内容\"}","activity":"com.dayi.activity.StudentQuestionDetailActivity","text":"推送内容","title":"有条新的消息"}},"random_min":0}
         */

        private NameValuePairsBeanXX nameValuePairs;

        public NameValuePairsBeanXX getNameValuePairs() {
            return nameValuePairs;
        }

        public void setNameValuePairs(NameValuePairsBeanXX nameValuePairs) {
            this.nameValuePairs = nameValuePairs;
        }

        public static class NameValuePairsBeanXX {
            /**
             * display_type : notification
             * extra : {"nameValuePairs":{"xuesitu_data":"{\"obj\":{\"subject_name\":\"语文\",\"expected_value\":5.00,\"question_id\":101,\"grade_name\":\"四年级\"},\"type\":1}"}}
             * msg_id : uufevxl156498746556101
             * body : {"nameValuePairs":{"after_open":"go_activity","ticker":"{\"data\":\"{\\\"obj\\\":{\\\"subject_name\\\":\\\"语文\\\",\\\"expected_value\\\":5.00,\\\"question_id\\\":101,\\\"grade_name\\\":\\\"四年级\\\"},\\\"type\\\":1}\",\"title\":\"有条新的消息\",\"content\":\"推送内容\"}","activity":"com.dayi.activity.StudentQuestionDetailActivity","text":"推送内容","title":"有条新的消息"}}
             * random_min : 0
             */

            private String display_type;
            private ExtraBean extra;
            private String msg_id;
            private BodyBean body;
            private int random_min;

            public String getDisplay_type() {
                return display_type;
            }

            public void setDisplay_type(String display_type) {
                this.display_type = display_type;
            }

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }

            public String getMsg_id() {
                return msg_id;
            }

            public void setMsg_id(String msg_id) {
                this.msg_id = msg_id;
            }

            public BodyBean getBody() {
                return body;
            }

            public void setBody(BodyBean body) {
                this.body = body;
            }

            public int getRandom_min() {
                return random_min;
            }

            public void setRandom_min(int random_min) {
                this.random_min = random_min;
            }

            public static class ExtraBean {
                /**
                 * nameValuePairs : {"xuesitu_data":"{\"obj\":{\"subject_name\":\"语文\",\"expected_value\":5.00,\"question_id\":101,\"grade_name\":\"四年级\"},\"type\":1}"}
                 */

                private NameValuePairsBean nameValuePairs;

                public NameValuePairsBean getNameValuePairs() {
                    return nameValuePairs;
                }

                public void setNameValuePairs(NameValuePairsBean nameValuePairs) {
                    this.nameValuePairs = nameValuePairs;
                }

                public static class NameValuePairsBean {
                    /**
                     * xuesitu_data : {"obj":{"subject_name":"语文","expected_value":5.00,"question_id":101,"grade_name":"四年级"},"type":1}
                     */

                    private String xuesitu_data;

                    public String getXuesitu_data() {
                        return xuesitu_data;
                    }

                    public void setXuesitu_data(String xuesitu_data) {
                        this.xuesitu_data = xuesitu_data;
                    }
                }
            }

            public static class BodyBean {
                /**
                 * nameValuePairs : {"after_open":"go_activity","ticker":"{\"data\":\"{\\\"obj\\\":{\\\"subject_name\\\":\\\"语文\\\",\\\"expected_value\\\":5.00,\\\"question_id\\\":101,\\\"grade_name\\\":\\\"四年级\\\"},\\\"type\\\":1}\",\"title\":\"有条新的消息\",\"content\":\"推送内容\"}","activity":"com.dayi.activity.StudentQuestionDetailActivity","text":"推送内容","title":"有条新的消息"}
                 */

                private NameValuePairsBeanX nameValuePairs;

                public NameValuePairsBeanX getNameValuePairs() {
                    return nameValuePairs;
                }

                public void setNameValuePairs(NameValuePairsBeanX nameValuePairs) {
                    this.nameValuePairs = nameValuePairs;
                }

                public static class NameValuePairsBeanX {
                    /**
                     * after_open : go_activity
                     * ticker : {"data":"{\"obj\":{\"subject_name\":\"语文\",\"expected_value\":5.00,\"question_id\":101,\"grade_name\":\"四年级\"},\"type\":1}","title":"有条新的消息","content":"推送内容"}
                     * activity : com.dayi.activity.StudentQuestionDetailActivity
                     * text : 推送内容
                     * title : 有条新的消息
                     */

                    private String after_open;
                    private String ticker;
                    private String activity;
                    private String text;
                    private String title;

                    public String getAfter_open() {
                        return after_open;
                    }

                    public void setAfter_open(String after_open) {
                        this.after_open = after_open;
                    }

                    public String getTicker() {
                        return ticker;
                    }

                    public void setTicker(String ticker) {
                        this.ticker = ticker;
                    }

                    public String getActivity() {
                        return activity;
                    }

                    public void setActivity(String activity) {
                        this.activity = activity;
                    }

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }
            }
        }
    }

    public static class ExtraBeanX {
        /**
         * xuesitu_data : {"obj":{"subject_name":"语文","expected_value":5.00,"question_id":101,"grade_name":"四年级"},"type":1}
         */

        private String xuesitu_data;

        public String getXuesitu_data() {
            return xuesitu_data;
        }

        public void setXuesitu_data(String xuesitu_data) {
            this.xuesitu_data = xuesitu_data;
        }
    }
}
