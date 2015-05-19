package com.greghilston.dailyreport;

/**
 * Represents an entire company
 */
public class Company extends Notable{
    private String name;
    private int quantity; // The number of employees on site for said company

    /**
     * @param name name of the company
     * @param quantity number of employees on site for this company
     */
    public Company(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
