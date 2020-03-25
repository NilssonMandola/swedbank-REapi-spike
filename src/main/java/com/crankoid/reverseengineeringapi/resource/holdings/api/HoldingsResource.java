package com.crankoid.reverseengineeringapi.resource.holdings.api;

import com.crankoid.reverseengineeringapi.resource.holdings.api.dto.HoldingsResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("holdings")
public interface HoldingsResource {
    @GetMapping("/{bic}")
    public HoldingsResponseDTO getHoldings(@PathVariable(required = true) String bic);
}
