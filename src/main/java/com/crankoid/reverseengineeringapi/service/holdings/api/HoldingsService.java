package com.crankoid.reverseengineeringapi.service.holdings.api;

import com.crankoid.reverseengineeringapi.service.holdings.api.model.Holding;

import java.util.List;

public interface HoldingsService {
    public List<Holding> getHoldings(String bic);
}
