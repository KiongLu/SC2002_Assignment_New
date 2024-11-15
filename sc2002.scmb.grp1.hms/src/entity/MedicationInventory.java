package entity;

public class MedicationInventory {
    private String medicationName;
    private int stockLevel;
    private int stockAlertLevel;

    public MedicationInventory(String medicationName, int stockLevel, int stockAlertLevel) {
        this.medicationName = medicationName;
        this.stockLevel = stockLevel;
        this.stockAlertLevel = stockAlertLevel;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public int getStockLevel() {
        return stockLevel;
    }
    
    public int getStockAlertLevel() {
        return stockAlertLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
    
    public void setStockAlertLevel(int stockAlertLevel) {
        this.stockAlertLevel = stockAlertLevel;
    }
}
