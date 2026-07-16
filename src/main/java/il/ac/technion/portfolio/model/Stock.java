package il.ac.technion.portfolio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "ticker", nullable = false, unique = true, length = 10)
    private String ticker;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "sector", length = 50)
    private String sector;

    public Stock() {}

    public Stock(String ticker, String companyName, String sector) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.sector = sector;
    }

    public Integer getStockId() { return stockId; }
    public void setStockId(Integer stockId) { this.stockId = stockId; }
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
}