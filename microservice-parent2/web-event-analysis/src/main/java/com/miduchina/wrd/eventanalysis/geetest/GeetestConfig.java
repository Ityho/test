package com.miduchina.wrd.eventanalysis.geetest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.08.08
 */
public class GeetestConfig {

    // 填入自己的captcha_id和private_key
    private static final String geetest_id = "33f0dcf1fcc11639569d22cd5e4907b8";
    private static final String geetest_key = "67ddd727890f57796b5fed53ae7e3758";
    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }

    public static final boolean isnewfailback() {
        return newfailback;
    }

}
