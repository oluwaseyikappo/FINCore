-- Create core schema for user tables
CREATE SCHEMA IF NOT EXISTS core;

-- USERS TABLE (matches User.java exactly)
CREATE TABLE IF NOT EXISTS core.users (
                                          id BIGSERIAL PRIMARY KEY,
                                          first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    role VARCHAR(50) NOT NULL
    );

-- TAX COMPUTATIONS TABLE (matches TaxComputation.java exactly)
CREATE TABLE IF NOT EXISTS tax_computations (
                                                id BIGSERIAL PRIMARY KEY,
                                                income DOUBLE PRECISION,
                                                created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    filing_status VARCHAR(50),
    tax_amount DOUBLE PRECISION,
    tax_credit DOUBLE PRECISION,
    net_tax DOUBLE PRECISION
    );
