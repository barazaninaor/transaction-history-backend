package il.ac.technion.portfolio.service;

import il.ac.technion.portfolio.model.Stock;
import il.ac.technion.portfolio.repository.StockRepository;
import jakarta.annotation.PostConstruct; // וודא שזה מיובא
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // This method runs automatically after the bean is initialized
    /*@PostConstruct
    public void seedInitialStocks() {
        if (stockRepository.count() == 0) {
            List<Stock> stocks = List.of(
                    new Stock("NVDA", "NVIDIA Corp", "Technology"),
                    new Stock("AAPL", "Apple Inc.", "Technology"),
                    new Stock("MSFT", "Microsoft Corp.", "Technology"),
                    new Stock("AMZN", "Amazon.com Inc", "Consumer Discretionary"),
                    new Stock("GOOGL", "Alphabet Inc. Class A Common Stock", "Technology"),
                    new Stock("AVGO", "Broadcom Inc. Common Stock", "Technology"),
                    new Stock("META", "Meta Platforms, Inc. Class A Common Stock", "Technology"),
                    new Stock("TSLA", "Tesla, Inc. Common Stock", "Automotive"),
                    new Stock("MU", "Micron Technology, Inc.", "Technology"),
                    new Stock("AMD", "Advanced Micro Devices", "Technology")
            );
            stockRepository.saveAll(stocks);
        }
    }*/

    // ... שאר המתודות (getAllStocks, getStockById, etc) נשארות כפי שהן
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Integer id) {
        return stockRepository.findById(id);
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Integer id, Stock stockDetails) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
        stock.setTicker(stockDetails.getTicker());
        stock.setCompanyName(stockDetails.getCompanyName());
        stock.setSector(stockDetails.getSector());
        return stockRepository.save(stock);
    }

    public boolean deleteStock(Integer id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return true;
        }
        return false;
    }
}