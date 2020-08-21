package com.delains.model.sales;

import java.math.BigDecimal;

public class DailyTotalSales {
    
    private String date;
    private BigDecimal totalSales;

    public DailyTotalSales(String date, BigDecimal totalSales) {
        this.date = date;
        this.totalSales = totalSales;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public String toString() {
        return "DailyTotalSales{" +
                "date='" + date + '\'' +
                ", totalSales=" + totalSales +
                '}';
    }
}
