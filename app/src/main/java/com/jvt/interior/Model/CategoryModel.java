package com.jvt.interior.Model;

public class CategoryModel {
    String itemImage, itemName, itemId, itemNode;

    public CategoryModel() {
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemNode() {
        return itemNode;
    }

    public void setItemNode(String itemNode) {
        this.itemNode = itemNode;
    }

    public CategoryModel(String itemImage, String itemName, String itemId, String itemNode) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemId = itemId;
        this.itemNode = itemNode;
    }
}
