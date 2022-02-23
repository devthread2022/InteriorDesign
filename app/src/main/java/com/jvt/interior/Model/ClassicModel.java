package com.jvt.interior.Model;

public class ClassicModel {
    String itemImage, itemId, itemNode;

    public ClassicModel() {
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
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

    public ClassicModel(String itemImage, String itemId, String itemNode) {
        this.itemImage = itemImage;
        this.itemId = itemId;
        this.itemNode = itemNode;
    }
}
