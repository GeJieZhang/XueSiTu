package com.user.bean;

public class PayZhiFuBaoBean {


    /**
     * code : 200
     * obj : {"body":"alipay_sdk=alipay-sdk-java-4.3.0.ALL&app_id=2016092900620920&biz_content=%7B%22body%22%3A%22%E5%AD%A6%E6%80%9D%E5%85%94%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%2220190819115542345603376104%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%AD%A6%E6%80%9D%E5%85%94%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=%E5%95%86%E6%88%B7%E5%A4%96%E7%BD%91%E5%8F%AF%E4%BB%A5%E8%AE%BF%E9%97%AE%E7%9A%84%E5%BC%82%E6%AD%A5%E5%9C%B0%E5%9D%80&sign=VYAJkep75Z%2BJffS3216VWyM0lU19%2Bc8AZXyaG%2BcC9N%2Foz0FfZ0SpoS9MApJ0xivY0FFzJZl7JUaAEB1o0ZKsdg0nTge7eb9Z%2BoJ%2Fef1a7lA1EKkWpE9s6D21rRqVKhlKUG8mfyfNqL4KO%2B3FQbf6ncaJWpXs3w0TtnqM4NVmSu0M8Bbdg4u0k0H36di5ciNy8FvEsz8W3g7KnfwQnjK8TZA8tmwFRaeTsM0fL1wHrPFqLxf4nSNi3U%2FolSN3lmcN%2FCBD3Wj7e76cw7Xh4P6JqIn4AVAVFbZmVIOcyVlKOs4966I2UmnhQe%2B5RQ4N4H6kdNrjrsg%2FvRWuQVfY56H49w%3D%3D&sign_type=RSA2×tamp=2019-08-19+11%3A55%3A42&version=1.0"}
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
         * body : alipay_sdk=alipay-sdk-java-4.3.0.ALL&app_id=2016092900620920&biz_content=%7B%22body%22%3A%22%E5%AD%A6%E6%80%9D%E5%85%94%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%2220190819115542345603376104%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%AD%A6%E6%80%9D%E5%85%94%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=%E5%95%86%E6%88%B7%E5%A4%96%E7%BD%91%E5%8F%AF%E4%BB%A5%E8%AE%BF%E9%97%AE%E7%9A%84%E5%BC%82%E6%AD%A5%E5%9C%B0%E5%9D%80&sign=VYAJkep75Z%2BJffS3216VWyM0lU19%2Bc8AZXyaG%2BcC9N%2Foz0FfZ0SpoS9MApJ0xivY0FFzJZl7JUaAEB1o0ZKsdg0nTge7eb9Z%2BoJ%2Fef1a7lA1EKkWpE9s6D21rRqVKhlKUG8mfyfNqL4KO%2B3FQbf6ncaJWpXs3w0TtnqM4NVmSu0M8Bbdg4u0k0H36di5ciNy8FvEsz8W3g7KnfwQnjK8TZA8tmwFRaeTsM0fL1wHrPFqLxf4nSNi3U%2FolSN3lmcN%2FCBD3Wj7e76cw7Xh4P6JqIn4AVAVFbZmVIOcyVlKOs4966I2UmnhQe%2B5RQ4N4H6kdNrjrsg%2FvRWuQVfY56H49w%3D%3D&sign_type=RSA2×tamp=2019-08-19+11%3A55%3A42&version=1.0
         */

        private String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
