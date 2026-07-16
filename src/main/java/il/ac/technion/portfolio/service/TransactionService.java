package il.ac.technion.portfolio.service;

import il.ac.technion.portfolio.model.Operation;
import il.ac.technion.portfolio.model.Stock;
import il.ac.technion.portfolio.model.Transaction;
import il.ac.technion.portfolio.repository.OperationRepository;
import il.ac.technion.portfolio.repository.StockRepository;
import il.ac.technion.portfolio.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class handling business logic for trade ledger Transactions.
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;
    private final OperationRepository operationRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              StockRepository stockRepository,
                              OperationRepository operationRepository) {
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
        this.operationRepository = operationRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> findByTicker(String ticker) {
        return transactionRepository.findByStockTicker(ticker);
    }

    /**
     * Saves a new transaction with detailed validation to help debug 500 errors.
     */
    public Transaction saveTransaction(Transaction transaction) {
        // 1. בדיקת קיום אובייקט העסקה
        if (transaction == null) {
            throw new RuntimeException("ERROR: Transaction object received is null. Check JSON structure.");
        }

        // 2. בדיקת Stock ו-Ticker
        if (transaction.getStock() == null || transaction.getStock().getTicker() == null) {
            throw new RuntimeException("ERROR: 'stock' object or 'ticker' field is missing or null in the request body.");
        }

        String ticker = transaction.getStock().getTicker().toUpperCase();
        Stock stock = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new RuntimeException("ERROR: Stock with ticker '" + ticker + "' not found in database."));

        // 3. בדיקת Operation ו-ID
        if (transaction.getOperation() == null || transaction.getOperation().getOperationId() == null) {
            throw new RuntimeException("ERROR: 'operation' object or 'operationId' field is missing or null in the request body.");
        }

        Integer opId = transaction.getOperation().getOperationId();
        Operation op = operationRepository.findById(opId)
                .orElseThrow(() -> new RuntimeException("ERROR: Operation with ID " + opId + " not found in database."));

        // 4. שמירה
        transaction.setStock(stock);
        transaction.setOperation(op);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Integer id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        if (transactionDetails.getStock() != null && transactionDetails.getStock().getTicker() != null) {
            Stock stock = stockRepository.findByTicker(transactionDetails.getStock().getTicker().toUpperCase())
                    .orElseThrow(() -> new RuntimeException("Stock not found for update"));
            transaction.setStock(stock);
        }

        if (transactionDetails.getOperation() != null && transactionDetails.getOperation().getOperationId() != null) {
            Operation op = operationRepository.findById(transactionDetails.getOperation().getOperationId())
                    .orElseThrow(() -> new RuntimeException("Operation not found for update"));
            transaction.setOperation(op);
        }

        transaction.setShares(transactionDetails.getShares());
        transaction.setPrice(transactionDetails.getPrice());

        return transactionRepository.save(transaction);
    }

    public boolean deleteTransaction(Integer id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}