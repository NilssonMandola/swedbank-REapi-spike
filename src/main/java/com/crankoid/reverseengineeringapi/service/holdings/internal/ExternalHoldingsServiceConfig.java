package com.crankoid.reverseengineeringapi.service.holdings.internal;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalHoldingsServiceConfig {
    @Bean("externalHoldingsServiceFactory")
    public FactoryBean<?> getBean() {
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(ExternalHoldingsServiceFactory.class);
        return bean;
    }
}
