package com.ecommerdedemo.ecommercedemo.business.concretes;

import com.ecommerdedemo.ecommercedemo.business.abstracts.HistoricalPriceService;
import com.ecommerdedemo.ecommercedemo.entities.concretes.HistoricalPrice;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Order;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;
import com.ecommerdedemo.ecommercedemo.repository.abstracts.HistoricalPriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class HistoricalPriceManager implements HistoricalPriceService {

    private final HistoricalPriceRepository historicalPriceRepository;

    public HistoricalPriceManager(HistoricalPriceRepository historicalPriceRepository) { // DI
        this.historicalPriceRepository = historicalPriceRepository;
    }

    @Override
    public void saveHistoricalPrice(Product product, double price) {
        HistoricalPrice historicalPrice = new HistoricalPrice();

        historicalPrice.setProduct(product);
        historicalPrice.setPrice(price);
        historicalPrice.setPurchaseDateTime(LocalDateTime.now());

        this.historicalPriceRepository.save(historicalPrice);
    }

    @Override
    public List<HistoricalPrice> getHistoricalPricesForRelatedProduct(Product product) {
        return this.historicalPriceRepository.findByProduct(product);
    }

    @Override
    public List<HistoricalPrice> getHistoricalPricesForRelatedOrder(Order order) {
        return this.historicalPriceRepository.findByOrder(order);
    }


}
