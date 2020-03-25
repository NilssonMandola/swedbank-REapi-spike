package com.crankoid.reverseengineeringapi.resource.holdings.api.dto;

import java.util.List;

public class HoldingsResponseDTO {
    private List<HoldingDTO> holdings;

    public List<HoldingDTO> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<HoldingDTO> holdings) {
        this.holdings = holdings;
    }
}
