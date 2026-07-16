-- ============================================================================
-- 1. DATABASE CREATION
-- ============================================================================
CREATE DATABASE PortfolioDB;
GO

USE PortfolioDB;
GO

-- ============================================================================
-- 2. TABLE CREATION (DDL)
-- ============================================================================

-- Create the Stocks table (The Referenced Resource)
CREATE TABLE Stocks
(
    stock_id INT IDENTITY(1,1) PRIMARY KEY,
    ticker VARCHAR(10) NOT NULL UNIQUE,
    -- Stock ticker symbol (e.g., AAPL, NVDA)
    company_name VARCHAR(100) NOT NULL,
    -- Full name of the company
    sector VARCHAR(50)
    -- Industry sector (e.g., Technology)
);
GO

-- Create the Operations lookup table (Dictionary table)
CREATE TABLE Operations
(
    operation_id INT PRIMARY KEY,
    -- Primary identifier (1 = BUY, 2 = SELL)
    operation_name VARCHAR(15) NOT NULL UNIQUE
    -- Name of the operation ('BUY' or 'SELL')
);
GO

-- Create the Transactions table (The Main Resource / Trading Log)
CREATE TABLE Transactions
(
    transaction_id INT IDENTITY(1,1) PRIMARY KEY,
    stock_id INT NOT NULL,
    -- Foreign Key referencing Stocks
    operation_id INT NOT NULL,
    -- Foreign Key referencing Operations
    shares INT NOT NULL,
    -- Number of shares traded in this transaction
    price DECIMAL(18, 2) NOT NULL,
    -- Price per share executed
    transaction_date DATETIME NOT NULL DEFAULT GETDATE(),
    -- Timestamp of the execution

    -- Define Foreign Key Constraints to maintain relational integrity
    CONSTRAINT FK_Transactions_Stocks FOREIGN KEY (stock_id) REFERENCES Stocks(stock_id),
    CONSTRAINT FK_Transactions_Operations FOREIGN KEY (operation_id) REFERENCES Operations(operation_id)
);
GO

-- ============================================================================
-- 3. DATA POPULATION (SEED DATA)
-- ============================================================================

-- Populate the permanent lookup values for Operations (Only BUY and SELL)
INSERT INTO Operations
    (operation_id, operation_name)
VALUES
    (1, 'BUY'),
    (2, 'SELL');
GO

-- Declare a table variable to temporarily hold inserted stock IDs for transactional mapping
DECLARE @InsertedStocks TABLE (
    stock_id INT,
    ticker VARCHAR(10)
);

-- Populate the Stocks table with 10 selected tech and mega-cap companies
INSERT INTO Stocks
    (ticker, company_name, sector)
OUTPUT inserted.stock_id, inserted.ticker INTO @InsertedStocks
VALUES
    ('NVDA', 'NVIDIA Corp', 'Technology'),
    ('AAPL', 'Apple Inc.', 'Technology'),
    ('MSFT', 'Microsoft Corp', 'Technology'),
    ('AMZN', 'Amazon.com Inc', 'Consumer Discretionary'),
    ('GOOGL', 'Alphabet Inc. Class A Common Stock', 'Technology'),
    ('AVGO', 'Broadcom Inc. Common Stock', 'Technology'),
    ('META', 'Meta Platforms, Inc. Class A Common Stock', 'Technology'),
    ('TSLA', 'Tesla, Inc. Common Stock', 'Automotive'),
    ('MU', 'Micron Technology, Inc.', 'Technology'),
    ('AMD', 'Advanced Micro Devices', 'Technology');

-- Populate the Transactions table with 10 initial BUY transactions (10 shares each)
INSERT INTO Transactions
    (stock_id, operation_id, shares, price, transaction_date)
SELECT stock_id, 1, 10,
    CASE ticker
        WHEN 'NVDA'  THEN 210.96
        WHEN 'AAPL'  THEN 315.32
        WHEN 'MSFT'  THEN 385.10
        WHEN 'AMZN'  THEN 245.34
        WHEN 'GOOGL' THEN 357.18
        WHEN 'AVGO'  THEN 399.97
        WHEN 'META'  THEN 669.21
        WHEN 'TSLA'  THEN 407.76
        WHEN 'MU'    THEN 979.30
        WHEN 'AMD'   THEN 557.89
    END,
    GETDATE()
FROM @InsertedStocks;
GO

-- Display all rows from the Stocks table
SELECT *
FROM Stocks;
GO

-- Display all rows from the Operations table
SELECT *
FROM Operations;
GO

-- Display all rows from the Transactions table
SELECT *
FROM Transactions;
GO

SELECT count(*) FROM Stocks;







-- Disable constraint checking temporarily to avoid conflict errors if needed, 
-- or drop tables in the precise order of their dependencies:

DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS Stocks;
DROP TABLE IF EXISTS Operations;