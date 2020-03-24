package com.crankoid.reverseengineeringapi.service.login.internal;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalLoginServiceConfig {
    @Bean("externalLoginServiceFactory")
    public FactoryBean<?> getBean() {
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(ExternalLoginServiceFactory.class);
        return bean;
    }
}
