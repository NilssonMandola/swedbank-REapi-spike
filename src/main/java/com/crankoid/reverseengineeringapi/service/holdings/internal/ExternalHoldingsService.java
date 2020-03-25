package com.crankoid.reverseengineeringapi.service.holdings.internal;

import com.crankoid.reverseengineeringapi.service.holdings.api.model.Holding;

import java.util.List;

public interface ExternalHoldingsService {
    public List<Holding> getHoldings();
}
