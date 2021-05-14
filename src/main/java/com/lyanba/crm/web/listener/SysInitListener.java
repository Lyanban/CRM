package com.lyanba.crm.web.listener;

import com.lyanba.crm.settings.domain.DicValue;
import com.lyanba.crm.settings.service.DicService;
import com.lyanba.crm.settings.service.impl.DicServiceImpl;
import com.lyanba.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @className: SysInitListener
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 10:23
 */
public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(">---------- 服务器缓存处理数据字典 ----------<");
        ServletContext application = sce.getServletContext();
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map = dicService.getAll();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            application.setAttribute(key, map.get(key));
        }
        System.out.println(">---------- 服务器缓存处理数据字典 ----------<");
    }
}
