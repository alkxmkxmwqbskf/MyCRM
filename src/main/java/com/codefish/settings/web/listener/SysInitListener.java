package com.codefish.settings.web.listener;
/**
 * @author codefish
 * @date 10/6/2021
 * @apinote
 */

import com.codefish.settings.domain.DictValue;
import com.codefish.settings.service.DictService;
import com.codefish.settings.service.impl.DictServiceImpl;
import com.codefish.util.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: codefish
 * @discription:
 */
public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        //ServletContextListener.super.contextInitialized(sce);
        System.out.println("Servlet context init...");
        ServletContext application = event.getServletContext();
        DictService service = (DictService) ServiceFactory.getService(new DictServiceImpl());
        //调用业务层
        Map<String, List<DictValue>> listMap = service.getAll();
        //解析map, 放入上下文对象
        Set<String> strings = listMap.keySet();
        for (String key: strings) {
            application.setAttribute(key, listMap.get(key));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //ServletContextListener.super.contextDestroyed(sce);
        System.out.println("Servlet context destroyed...");
    }
}
