package il.ac.technion.portfolio.controller;

import il.ac.technion.portfolio.model.Stock;
import il.ac.technion.portfolio.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing full CRUD endpoints for managing Stock assets.
 */
@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
public class StockController {

    private final StockService stockService;

    // Constructor injection for StockService
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * HTTP GET Endpoint to retrieve all available stocks.
     * URI: GET /api/stocks
     */
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * HTTP GET Endpoint to retrieve a single stock by its ID.
     * URI: GET /api/stocks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Integer id) {
        return stockService.getStockById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * HTTP POST Endpoint to create and save a new stock asset.
     * URI: POST /api/stocks
     */
    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    /**
     * HTTP PUT Endpoint to update an existing stock by its ID.
     * URI: PUT /api/stocks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id, @RequestBody Stock stockDetails) {
        try {
            Stock updatedStock = stockService.updateStock(id, stockDetails);
            return ResponseEntity.ok(updatedStock);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * HTTP DELETE Endpoint to remove a stock by its ID.
     * URI: DELETE /api/stocks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        boolean deleted = stockService.deleteStock(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Status 204
        }
        return ResponseEntity.notFound().build(); // Status 404
    }
}