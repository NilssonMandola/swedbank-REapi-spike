package com.crankoid.reverseengineeringapi.service.account.internal;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalAccountServiceConfig {
    @Bean("externalAccountServiceFactory")
    public FactoryBean<?> getBean() {
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(ExternalAccountServiceFactory.class);
        return bean;
    }
}
