package il.ac.technion.portfolio.repository;

import il.ac.technion.portfolio.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // אל תשכח את הייבוא הזה!

/**
 * Repository interface for Stock entity.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    // הוסף את השורה הזו כדי ש-Spring יוכל למצוא מניה לפי הסימול שלה
    Optional<Stock> findByTicker(String ticker);
}