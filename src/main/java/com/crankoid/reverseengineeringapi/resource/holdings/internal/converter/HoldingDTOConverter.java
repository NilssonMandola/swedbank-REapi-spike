package com.crankoid.reverseengineeringapi.resource.holdings.internal.converter;

import com.crankoid.reverseengineeringapi.resource.holdings.api.dto.HoldingDTO;
import com.crankoid.reverseengineeringapi.service.holdings.api.model.Holding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HoldingDTOConverter implements Converter<Holding, HoldingDTO> {
    @Override
    public HoldingDTO convert(Holding source) {
        HoldingDTO dto = new HoldingDTO();
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setAccountNumber(source.getAccountNumber());
        return dto;
    }

    public List<HoldingDTO> convert(List<Holding> source) {
        return source.stream().map(a -> convert(a)).collect(Collectors.toList());
    }
}
