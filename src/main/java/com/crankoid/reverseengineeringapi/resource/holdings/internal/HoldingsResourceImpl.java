package com.crankoid.reverseengineeringapi.resource.holdings.internal;

import com.crankoid.reverseengineeringapi.resource.holdings.api.HoldingsResource;
import com.crankoid.reverseengineeringapi.resource.holdings.api.dto.HoldingDTO;
import com.crankoid.reverseengineeringapi.resource.holdings.api.dto.HoldingsResponseDTO;
import com.crankoid.reverseengineeringapi.resource.holdings.internal.converter.HoldingDTOConverter;
import com.crankoid.reverseengineeringapi.service.holdings.api.HoldingsService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HoldingsResourceImpl implements HoldingsResource {
    private HoldingsService holdingsService;
    private HoldingDTOConverter converter;

    public HoldingsResourceImpl(HoldingsService holdingsService, HoldingDTOConverter converter) {
        this.holdingsService = holdingsService;
        this.converter = converter;
    }

    @Override
    public HoldingsResponseDTO getHoldings(String bic) {
        HoldingsResponseDTO holdingsResponse = new HoldingsResponseDTO();
        List<HoldingDTO> holdings = converter.convert(holdingsService.getHoldings(bic));
        holdingsResponse.setHoldings(holdings);
        return holdingsResponse;
    }
}
