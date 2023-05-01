package com.example.backend1projekt.models;

import java.util.List;

public class OrderFormAttribute {

    private String customerSsn;
    private List<Long> itemIds;

    public String getCustomerSsn() {
        return customerSsn;
    }

    public void setCustomerSsn(String customerId) {
        this.customerSsn = customerId;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    @Override
    public String toString() {
        return "OrderFormAttribute{" +
                "customerSsn='" + customerSsn + '\'' +
                ", itemIds=" + itemIds +
                '}';
    }
}
