package com.crankoid.reverseengineeringapi.service.holdings.internal;

import com.crankoid.reverseengineeringapi.service.holdings.api.HoldingsService;
import com.crankoid.reverseengineeringapi.service.holdings.api.model.Holding;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldingsServiceImpl implements HoldingsService {
    private ExternalHoldingsServiceFactory factory;

    public HoldingsServiceImpl(ExternalHoldingsServiceFactory factory) {
        this.factory = factory;
    }
    @Override
    public List<Holding> getHoldings(String bic) {
        return factory.getExternalHoldingsService(bic + "/HOLDING").getHoldings();
    }
}
