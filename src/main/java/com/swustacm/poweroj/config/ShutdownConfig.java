package com.swustacm.poweroj.config;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Graceful Shutdown
 * @author yu on 2020/07/24.
 */
@Configuration
public class ShutdownConfig {


    @Bean
    public GracefulShutdown gracefulShutdown(@Value("${server.shutdown-wait-seconds:30}") int shutdownWaitSeconds) {
        GracefulShutdown gracefulShutdown = new GracefulShutdown();
        gracefulShutdown.setShutdownWaitSeconds(shutdownWaitSeconds);
        return gracefulShutdown;
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdown gracefulShutdown) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(gracefulShutdown);
        return factory;
    }

    public static class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
        private static final Logger log = LoggerFactory.getLogger(GracefulShutdown.class);
        private volatile Connector connector;

        private int shutdownWaitSeconds;

        public void setShutdownWaitSeconds(int shutdownWaitSeconds) {
            this.shutdownWaitSeconds = shutdownWaitSeconds;
        }

        @Override
        public void customize(Connector connector) {
            this.connector = connector;
        }

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            this.connector.pause();
            Executor executor = this.connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {
                try {
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                    threadPoolExecutor.shutdown();
                    if (!threadPoolExecutor.awaitTermination(shutdownWaitSeconds, TimeUnit.SECONDS)) {
                        log.warn("Tomcat thread pool did not shut down gracefully within {} seconds. " +
                                "Proceeding with forceful shutdown", shutdownWaitSeconds);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
