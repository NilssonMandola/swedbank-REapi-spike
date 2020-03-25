package com.crankoid.reverseengineeringapi.resource.holdings.api;

import com.crankoid.reverseengineeringapi.resource.holdings.api.dto.HoldingsResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("holdings")
@Tag(name = "Holdings")
public interface HoldingsResource {
    @GetMapping("/{bic}")
    public HoldingsResponseDTO getHoldings(@PathVariable(required = true) String bic);
}
