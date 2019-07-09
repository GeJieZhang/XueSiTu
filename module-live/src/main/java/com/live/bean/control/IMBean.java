package com.live.bean.control;

import java.util.List;

public class IMBean {


    /**
     * actionType : 7
     * object : [{"voice":0,"user_id":"21","phone":"13126815573","identity":"1","user_name":"教师13126815573","sessionId":"5","camera":0},{"voice":0,"phone":"13256560000","identity":"2","name":"学员13256","photo_url":"http://pu00k0ssj.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG","camera":0}]
     */

    private int actionType;
    private ObjectBean object;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * voice : 0
         * user_id : 21
         * phone : 13126815573
         * identity : 1
         * user_name : 教师13126815573
         * sessionId : 5
         * camera : 0
         * name : 学员13256
         * photo_url : http://pu00k0ssj.bkt.clouddn.com/FgOLkuCYx3tqsJQAIwmXK3PHwTmG
         */

        private int voice;
        private String user_id;
        private String phone;
        private String identity;
        private String user_name;
        private String sessionId;
        private int camera;
        private String name;
        private String photo_url;
        private String messageId;
        private String userIcon;
        private String roomName;
        private String message;
        private String userName;
        private int type;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getVoice() {
            return voice;
        }

        public void setVoice(int voice) {
            this.voice = voice;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getCamera() {
            return camera;
        }

        public void setCamera(int camera) {
            this.camera = camera;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "voice=" + voice +
                    ", user_id='" + user_id + '\'' +
                    ", phone='" + phone + '\'' +
                    ", identity='" + identity + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", sessionId='" + sessionId + '\'' +
                    ", camera=" + camera +
                    ", name='" + name + '\'' +
                    ", photo_url='" + photo_url + '\'' +
                    ", messageId='" + messageId + '\'' +
                    ", userIcon='" + userIcon + '\'' +
                    ", roomName='" + roomName + '\'' +
                    ", message='" + message + '\'' +
                    ", userName='" + userName + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "IMBean{" +
                "actionType=" + actionType +
                ", object=" + object +
                '}';
    }
}
