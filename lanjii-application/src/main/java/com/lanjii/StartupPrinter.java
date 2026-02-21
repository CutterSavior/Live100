package com.lanjii;

import lombok.SneakyThrows;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * 启动信息打印器
 *
 * @author lanjii
 */
@Component
public class StartupPrinter implements ApplicationListener<ServletWebServerInitializedEvent> {

    @SneakyThrows
    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        String ip = InetAddress.getLocalHost().getHostAddress();
        int port = event.getWebServer().getPort();
        String contextPath = event.getApplicationContext().getServletContext().getContextPath();

        System.out.println("\n" + String.format("""
                        ==============================
                        应用启动成功！
                        本地访问: http://localhost:%d%s
                        网络访问: http://%s:%d%s
                        ==============================
                        """,
                port, contextPath,
                ip, port, contextPath,
                ip, port, contextPath));
    }
}