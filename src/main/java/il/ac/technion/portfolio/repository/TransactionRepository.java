package il.ac.technion.portfolio.repository;

import il.ac.technion.portfolio.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // שימוש ב-Query מפורש שפותר בעיות מיפוי מורכבות
    @Query("SELECT t FROM Transaction t JOIN t.stock s WHERE s.ticker = :ticker")
    List<Transaction> findByStockTicker(@Param("ticker") String ticker);
}