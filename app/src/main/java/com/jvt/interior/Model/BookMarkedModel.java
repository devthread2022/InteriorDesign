package com.jvt.interior.Model;

public class BookMarkedModel {
    String picture, productName, productDescription, productSummary, productId, productCategory;
    Double rating;

    public BookMarkedModel() {
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductSummary() {
        return productSummary;
    }

    public void setProductSummary(String productSummary) {
        this.productSummary = productSummary;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public BookMarkedModel(String picture, String productName, String productDescription, String productSummary, String productId, String productCategory, Double rating) {
        this.picture = picture;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productSummary = productSummary;
        this.productId = productId;
        this.productCategory = productCategory;
        this.rating = rating;
    }
}
