package com.login.testlogin;


import android.text.TextUtils;

import java.util.regex.Pattern;


/**
 * 表单验证工具类
 * Created by lk on 2016/7/28.
 */
public class FormValidation {

    /**
     * QQ校验
     */
    public static boolean isQQ(String qq) {
        return match(
                qq, "[1-9][0-9]{4,}");
    }

    /**
     * 联系电话(手机/电话皆可)验证
     */
    public final static boolean isTel(String text) {
        return (isMobile(text) || isPhone(text));
    }

    /**
     * 电话号码验证
     */
    public final static boolean isPhone(String text) {
        return match(text, "^(400|010|02\\d|0[3-9]\\d{2})-?\\d{6,8}$");
    }

    /**
     * 手机号码验证
     */
    public final static boolean isMobile(String text) {
        if (text.length() != 11) return false;
        return match(text,
                "^[1]([3][0-9]{1}|34|35|36|37|38|39|47|50|51|52|57|58|59|78|82|83|84|87|88|30|31|32|45|55|56|71|75|76|85|86|33|49|53|73|77|80|81|89|70)[0-9]{8}$");
    }

    /**
     * weixin 数字、字母、下划线
     */
    public static boolean isWeChat(String text) {
        return match(text, "^[a-z_\\d]+$");
    }

    /**
     * 匹配Email地址
     */
    public static boolean isEmail(String str) {
        return match(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    /**
     * 匹配密码
     */
    public static boolean isPassWord(String text) {
        return match(text, "^[a-z\\d]+$");
    }


    /**
     * 判断是否为合法字符(a-zA-Z0-9-_)
     */
    public final static boolean isRightfulString(String text) {
        return match(text, "^[A-Za-z0-9_-]+$");
    }

    /**
     * 判断英文字符(a-zA-Z)
     */
    public final static boolean isEnglish(String text) {
        return match(text, "^[A-Za-z]+$");
    }

    /**
     * 匹配汉字
     */
    public final static boolean isChinese(String text) {
        return match(text, "^[\u4e00-\u9fa5]+$");
    }


    /**
     * 正则表达式匹配
     */
    private final static boolean match(String text, String reg) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(reg))
            return false;
        return Pattern.compile(reg).matcher(text).matches();
    }

}
