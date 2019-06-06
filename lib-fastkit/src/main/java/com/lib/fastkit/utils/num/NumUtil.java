package com.lib.fastkit.utils.num;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数字处理工具类
 * @author zhengyingshun
 * @Data 2017年9月26日
 */
public class NumUtil {

    /**
     * 向下取整
     * @param n
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static int floor(double n) {
        return (int) Math.floor(n);
    }

    /**
     * 向上取整
     * @param n
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static int ceil(double n) {
        return (int) Math.ceil(n);
    }

    /**
     * 四舍五入取整
     * @param n
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static int round(double n) {
        return (int) Math.round(n);
    }

    /**
     * 四舍五入取scale位小数
     * @param e
     * @param scale
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static double scaleRound(double e, int scale) {
        return new BigDecimal(e).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入取scale位小数
     * @param e
     * @param scale
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static String scaleRoundStr(double e, int scale) {
        return scaleRound(e, scale) + "";
    }

    /**
     * 保留scale位小数
     * @param e
     * @param scale
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static double scaleFloor(double e, int scale) {
        return new BigDecimal(e).setScale(2, RoundingMode.DOWN).doubleValue();
    }

    /**
     * 保留scale位小数
     * @param e
     * @param scale
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static String scaleFloorStr(double e, int scale) {
        return scaleFloorStr(e, scale) + "";
    }

    /**
     * 求log(x)(y)，利用换底公式
     * @param x 底
     * @param y
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static double log(double x, double y) {
        return Math.log(y) / Math.log(x);
    }

    /**
     * 去掉小数点后无效的0
     * @param e
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static String subZeroAndDot(double e) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(e);
    }

    /**
     * 去掉小数点后无效的0
     * @param e
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static String subZeroAndDot(String e) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(e);
    }

    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     * @param v
     * @param scale
     * @return
     * @author zhengyingshun
     * @Data 2017年9月26日
     */
    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

}
