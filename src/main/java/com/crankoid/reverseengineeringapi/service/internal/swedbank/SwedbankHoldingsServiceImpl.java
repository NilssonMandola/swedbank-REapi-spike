package com.crankoid.reverseengineeringapi.service.internal.swedbank;

import com.crankoid.reverseengineeringapi.service.holdings.api.model.Holding;
import com.crankoid.reverseengineeringapi.service.holdings.internal.ExternalHoldingsService;
import com.crankoid.reverseengineeringapi.service.internal.swedbank.client.SwedbankClient;
import com.crankoid.reverseengineeringapi.service.internal.swedbank.converter.HoldingConverter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service("SWEDSESS/HOLDING")
public class SwedbankHoldingsServiceImpl implements ExternalHoldingsService {
    private SwedbankClient swedbankClient;
    private HoldingConverter converter;

    public SwedbankHoldingsServiceImpl(SwedbankClient swedbankClient, HoldingConverter converter) {
        this.swedbankClient = swedbankClient;
        this.converter = converter;
    }

    @Override
    public List<Holding> getHoldings() {
        try {
            return converter.convert(swedbankClient.getHoldings());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
