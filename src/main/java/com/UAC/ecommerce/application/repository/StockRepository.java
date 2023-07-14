package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.Stock;

import java.util.List;

public interface StockRepository {
    Stock saveStock(Stock stock);
    List<Stock> getStockByProduct(Product Product);

}
