package il.ac.technion.portfolio.service;

import il.ac.technion.portfolio.model.Operation;
import il.ac.technion.portfolio.repository.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class OperationService implements CommandLineRunner {

    private final OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (operationRepository.count() == 0) {
            operationRepository.save(new Operation(1, "BUY"));
            operationRepository.save(new Operation(2, "SELL"));
            System.out.println(">>> Operations (BUY/SELL) initialized.");
        }
    }
}