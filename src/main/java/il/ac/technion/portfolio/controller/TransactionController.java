package il.ac.technion.portfolio.controller;

import il.ac.technion.portfolio.model.Transaction;
import il.ac.technion.portfolio.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing full CRUD endpoints for managing ledger Transactions.
 */
@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    // Constructor injection
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 1. GET: שליפת כל העסקאות
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // 2. GET: שליפת עסקה בודדת לפי ID
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Integer id) {
        return transactionService.getTransactionById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    @GetMapping("/search")
    public List<Transaction> getTransactionsByTicker(@RequestParam("ticker") String ticker) {
        return transactionService.findByTicker(ticker);
    }

    // 3. POST: יצירת עסקה חדשה
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    // 4. PUT: עדכון עסקה קיימת
    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Integer id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    // 5. DELETE: מחיקת עסקה
    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Integer id) {
        boolean deleted = transactionService.deleteTransaction(id);
        return deleted ? "Transaction deleted successfully" : "Transaction not found";
    }
}