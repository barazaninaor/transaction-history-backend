package il.ac.technion.portfolio.repository;

import il.ac.technion.portfolio.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Operation lookup entity (BUY/SELL).
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
}