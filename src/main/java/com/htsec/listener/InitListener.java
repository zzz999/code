package com.htsec.listener;

import com.htsec.commons.utils.SpringContextUtil;
import com.htsec.schedule.executors.SingleThreadScheduleExecutor;
import com.htsec.service.impl.UpdateHotSearchTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.TimeUnit;

/**
 * Created by bernard on 2017/7/6.
 */
public class InitListener  implements ServletContextListener {




    @Override
    public void contextInitialized(ServletContextEvent sce) {

       // sce.getServletContext()

      //  ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext( sce.getServletContext()); //获得Applicationcontext对象

       // ApplicationContext   applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);//获得Applicationcontext对象

       // nationServiceService = (UpdateHotSearchTaskServiceImpl) context.getBean("updateHotSearchTaskServiceImpl");//取beanservice文件
        //ApplicationContext ac2 = WebApplicationContextUtils.getWebApplicationContext( sc);
     //   UpdateHotSearchTaskServiceImpl service =(UpdateHotSearchTaskServiceImpl) context.getBean("updateHotSearchTaskServiceImpl");;
     //   SingleThreadScheduleExecutor.getService().scheduleAtFixedRate(service,0,30, TimeUnit.MINUTES);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
