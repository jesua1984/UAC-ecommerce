package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.application.repository.StockRepository;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.Stock;

import java.util.List;

public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock saveStock(Stock stock){
        return  stockRepository.saveStock(stock);
    }

    public List<Stock> getStockByProduct(Product product){
        return stockRepository.getStockByProduct(product);
    }

}
