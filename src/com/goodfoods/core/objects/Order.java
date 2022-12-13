package com.goodfoods.core.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Cloneable {


    private InventoryItem product;
    private List<WorkOrder> workOrders;
    private String POID;
    private String orderID;
    private String itemName;
    private String itemId;
    private String buyer;
    private List<InventoryItem> materials;
    private int currentWorkOrderID;
    private boolean isDone;
    private double markUp;
    private double price;

    public Order(InventoryItem product, List<WorkOrder> workOrders, String POID, String orderID, String itemName, String itemId, String buyer, List<InventoryItem> materials, int currentWorkOrderID, boolean isDone) {
        this.product = product;
        this.workOrders = workOrders;
        this.POID = POID;
        this.orderID = orderID;
        this.itemName = itemName;
        this.itemId = itemId;
        this.buyer = buyer;
        this.materials = new ArrayList<>();
        materials.forEach(item -> this.materials.add(item.clone()));
        this.currentWorkOrderID = currentWorkOrderID;
        this.isDone = isDone;
    }

    public Order(InventoryItem product, List<InventoryItem> materials) {
        this.materials = new ArrayList<>();
        this.product = product;
        workOrders = new ArrayList<>();
        itemName = product.getItemDescription();
        itemId = product.getPartNumber();
        orderID = Inventory.getInstance().createOrderId();
        buyer = product.getVendor();
        POID = orderID.replace("O", "PO");
        materials.forEach(material -> {
            InventoryItem newMat = material.clone();
            newMat.setQuantityNeeded(0);
            newMat.setQuantityInHouse(0);
            this.materials.add(newMat);
        });
        currentWorkOrderID = 0;
        isDone = false;
    }

    public Order() {
        this.product = null;
        itemName = null;
        itemId = null;
        orderID = null;
        buyer = null;
        POID = null;
        this.materials = new ArrayList<>();
        currentWorkOrderID = 0;
        isDone = false;
    }

    public InventoryItem getProduct() {
        return product;
    }

    public List<InventoryItem> getMaterials() {
        return materials;
    }

    public float getCost() {
        float cost = 0.00f;
        for (InventoryItem material : materials) {
            cost += (material.getQuantityNeeded() * material.getCost());
        }
        return cost;
    }

    public String getCostAsString() {
        return new DecimalFormat("#.##").format(getCost());
    }

    public void setProduct(InventoryItem product) {
        this.product = product;
    }

    public String getPOID() {
        return POID;
    }

    public void setPOID(String POID) {
        this.POID = POID;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        product.setItemDescription(itemName);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setMaterials(List<InventoryItem> materials) {
        this.materials = materials;
    }

    public List<WorkOrder> getWorkOrders() {
        if(this.workOrders == null) {
            this.workOrders = new ArrayList<>();
        }
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    public int getCurrentWorkOrderID() {
        return currentWorkOrderID;
    }

    public void setCurrentWorkOrderID(int currentWorkOrderID) {
        this.currentWorkOrderID = currentWorkOrderID;
    }

    public void addWorkOrder(WorkOrder order) {
        if(this.workOrders == null) {
            this.workOrders = new ArrayList<>();
        }
        this.workOrders.add(order);
    }

    public int getNextWorkId() {
        int current = currentWorkOrderID;
        currentWorkOrderID += 1;
        return current;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public Order clone() {
        return new Order(product, workOrders, POID, orderID, itemName, itemId, buyer, materials, currentWorkOrderID, isDone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return currentWorkOrderID == order.currentWorkOrderID &&
                Objects.equals(product, order.product) &&
                Objects.equals(workOrders, order.workOrders) &&
                Objects.equals(POID, order.POID) &&
                Objects.equals(orderID, order.orderID) &&
                Objects.equals(itemName, order.itemName) &&
                Objects.equals(itemId, order.itemId) &&
                Objects.equals(buyer, order.buyer) &&
                Objects.equals(materials, order.materials);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "product=" + product +
                ", POID='" + POID + '\'' +
                ", orderID='" + orderID + '\'' +
                ", materials=" + materials +
                '}';
    }
}
