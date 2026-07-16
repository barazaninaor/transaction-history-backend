package il.ac.technion.portfolio.model;

import jakarta.persistence.*;

/**
 * Entity class mapping to the Operations table.
 * Represents lookup data for transaction types (e.g., BUY, SELL).
 */
@Entity
@Table(name = "Operations")
public class Operation {

    @Id
    @Column(name = "operation_id")
    private Integer operationId;

    @Column(name = "operation_name", nullable = false, unique = true, length = 15)
    private String operationName;

    // Default constructor required by JPA
    public Operation() {}

    // Constructor for creating lookup entries
    public Operation(Integer operationId, String operationName) {
        this.operationId = operationId;
        this.operationName = operationName;
    }

    // Getters and Setters
    public Integer getOperationId() { return operationId; }
    public void setOperationId(Integer operationId) { this.operationId = operationId; }
    public String getOperationName() { return operationName; }
    public void setOperationName(String operationName) { this.operationName = operationName; }
}