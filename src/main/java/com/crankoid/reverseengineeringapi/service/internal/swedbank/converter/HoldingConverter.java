package com.crankoid.reverseengineeringapi.service.internal.swedbank.converter;

import com.crankoid.reverseengineeringapi.service.holdings.api.model.Holding;
import com.crankoid.reverseengineeringapi.service.internal.swedbank.client.model.SwedbankHolding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HoldingConverter implements Converter<SwedbankHolding, Holding> {
    @Override
    public Holding convert(SwedbankHolding source) {
        Holding holding = new Holding();
        holding.setId(source.getId());
        holding.setName(source.getName());
        holding.setAccountNumber(source.getAccountNumber());

        return holding;
    }

    public List<Holding> convert(List<SwedbankHolding> source) {
        return source.stream().map(a -> convert(a)).collect(Collectors.toList());
    }
}
