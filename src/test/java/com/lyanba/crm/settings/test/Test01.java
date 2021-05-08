package com.lyanba.crm.settings.test;

import com.lyanba.crm.utils.DateTimeUtil;
import org.junit.Test;

/**
 * @className: Test01
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/9 00:06
 */
public class Test01 {
    /**
     * 测试验证账号是否失效的方法
     * 系统时间工具类的使用
     */
    @Test
    public void test01() {
        String expireTime = "2020-10-03 13:13:13";
        String currentTime = DateTimeUtil.getSysTime();
        System.out.println(expireTime.compareTo(currentTime) < 0 ? "账号已失效" : "账号在有效期内");
    }

    /**
     * 测试验证访问IP地址是否受限
     */
    @Test
    public void test02() {
        String browserIp = "192.168.0.1";
        String allowIps = "192.168.0.1,192.168.0.2,192.168.0.3";
        System.out.println(allowIps.contains(browserIp) ? "允许此IP访问" : "IP地址受限");
    }
}
