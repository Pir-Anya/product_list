package com.example.anuta_list;

import java.util.List;

public interface IDatabaseHandler {

    public void addProduct(Product Product);
    public Product getProduct(int id);
    public List<Product> getAllProducts();
    public int getProductsCount();
    public int updateProduct(Product Product);
    public void deleteProduct(Product Product);
    public void deleteAll();

}
