package com.goodfoods.core.objects;

import com.sun.istack.internal.Nullable;

import java.util.Objects;

public class InventoryItem implements Cloneable {

    private String partNumber;
    private String itemDescription;
    private int quantityInHouse;
    private int quantityNeeded;
    private int quantityInUse;
    private String PONumber;
    private int minQty;
    private float cost;
    private String vendor;

    @Nullable
    private int totalAmount;

    public InventoryItem(String partNumber, String itemDescription, int quantityInHouse, int quantityNeeded, int quantityInUse, String PONumber, int minQty, float cost, String vendor) {
        this.partNumber = partNumber;
        this.itemDescription = itemDescription;
        this.quantityInHouse = quantityInHouse;
        this.quantityNeeded = quantityNeeded;
        this.PONumber = PONumber;
        this.minQty = minQty;
        this.quantityInUse = quantityInUse;
        this.cost = cost;
        this.vendor = vendor;
    }

    public InventoryItem(String partNumber, String itemDescription, int quantityInHouse, float cost, String vendor) {
        this.partNumber = partNumber;
        this.itemDescription = itemDescription;
        this.quantityInHouse = quantityInHouse;
        this.quantityNeeded = 0;
        this.quantityInUse = 0;
        this.PONumber = "N/A";
        this.minQty = 500;
        this.cost = cost;
        this.vendor = vendor;
    }

    public InventoryItem(String partNumber, String itemDescription, int quantityNeeded) {
        this.partNumber = partNumber;
        this.itemDescription = itemDescription;
        this.quantityNeeded = quantityNeeded;
        this.PONumber = "N/A";
        this.minQty = 0;
    }

    public InventoryItem() {
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getQuantityInHouse() {
        return quantityInHouse;
    }

    public void setQuantityInHouse(int quantityInHouse) {
        this.quantityInHouse = quantityInHouse;
    }

    public int getQuantityNeeded() {
        return quantityNeeded;
    }

    public void setQuantityNeeded(int quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getPONumber() {
        return PONumber;
    }

    public void setPONumber(String PONumber) {
        this.PONumber = PONumber;
    }

    public int getMinQty() {
        return minQty;
    }

    public void setMinQty(int minQty) {
        this.minQty = minQty;
    }

    public int getQuantityInUse() {
        return quantityInUse;
    }

    public void setQuantityInUse(int quantityInUse) {
        this.quantityInUse = quantityInUse;
    }

    @Override
    public String toString() {
        return partNumber + ":" + itemDescription + ":" + quantityInHouse + ":" + quantityNeeded + ":" + quantityInUse + ":" + PONumber + ":" + minQty + ":" + cost + ":" + vendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return quantityInHouse == that.quantityInHouse &&
                quantityNeeded == that.quantityNeeded &&
                Float.compare(that.cost, cost) == 0 &&
                Objects.equals(partNumber, that.partNumber) &&
                Objects.equals(itemDescription, that.itemDescription) &&
                Objects.equals(PONumber, that.PONumber) &&
                Objects.equals(minQty, that.minQty) &&
                Objects.equals(vendor, that.vendor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, itemDescription, quantityInHouse, quantityNeeded, PONumber, minQty, cost, vendor);
    }

    @Override
    public InventoryItem clone() {
        return new InventoryItem(partNumber, itemDescription, quantityInHouse, quantityNeeded, quantityInUse, PONumber, minQty, cost, vendor);
    }
}
