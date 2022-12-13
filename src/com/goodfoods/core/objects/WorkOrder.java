package com.goodfoods.core.objects;

import java.util.ArrayList;
import java.util.List;

public class WorkOrder {

    private Integer workOrderId;
    private transient Order order;
    private ArrayList<Job> work;
    private String workString;
    private List<InventoryItem> materialsNeeded;
    private List<InventoryItem> materialsUsed;

    public WorkOrder(Order order) {
        workOrderId = order.getNextWorkId();
        this.order = order;
        this.work = new ArrayList<>();
        this.materialsNeeded = order.getMaterials();
        this.materialsUsed = new ArrayList<>();
        this.workString = "";
    }

    public WorkOrder() {
    }

    public void addWork(Job job) {
        if(work.size() < 1) {
            workString += job.getJobType().name();
        } else {
            workString += ", " + job.getJobType().name();
        }
        work.add(job);
    }

    public void addWork(JobType job, int hours) {
        work.add(new Job(job, hours));
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Job> getWork() {
        return work;
    }

    public void setWork(ArrayList<Job> work) {
        this.work = work;
    }

    public List<InventoryItem> getMaterialsNeeded() {
        return materialsNeeded;
    }

    public void setMaterialsNeeded(List<InventoryItem> materialsNeeded) {
        this.materialsNeeded = materialsNeeded;
    }

    public List<InventoryItem> getMaterialsUsed() {
        return materialsUsed;
    }

    public void setMaterialsUsed(List<InventoryItem> materialsUsed) {
        this.materialsUsed = materialsUsed;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getWorkString() {
        return workString;
    }

    public void setWorkString(String workString) {
        this.workString = workString;
    }
}
