/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Product;
import models.Brand;
import models.Category;
import java.util.ArrayList;
import java.util.List;
import filemanager.FileManager;
import filemanager.IFileManager;
import java.util.Collections;

/**
 *
 * @author Asus
 */
public class ProductList implements IItemManager<Product> {

    private static final String FILE_NAME = "products.txt";

    private List<Product> list;
    private BrandList brandList;
    private CategoryList categoryList;

    public ProductList() {
        this.brandList = new BrandList();
        this.categoryList = new CategoryList();
        this.list = this.loadFile();
    }

    public List<Product> getList() {
        return this.list;
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();

        for (Product product : this.list) {
            if (product.getName().toUpperCase().contains(name.toUpperCase())) {
                result.add(product);
            }
        }
        if (result.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return result;
    }

    @Override
    public Product getItem(String id) {
        Product result = null;

        for (Product product : this.list) {
            if (product.getId().equalsIgnoreCase(id)) {
                result = product;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean add(Product item) {
        if (item == null) {
            return false;
        }
        return this.list.add(item);
    }

    @Override
    public boolean update(String id, Product params) {
        if (params == null) {
            return false;
        }
        if (!this.checkExistId(id)) {
            return false;
        }
        Product oldProduct = this.getItem(id);

        int position = this.list.indexOf(oldProduct);
        if (position == -1) {
            return false;
        }

        this.list.set(position, params);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (!this.checkExistId(id)) {
            return false;
        }
        return this.list.remove(this.getItem(id));
    }

    public void saveFile() {
        IFileManager fileManager = new FileManager();
        List<String> listString = new ArrayList<>();
        for (Product product : this.list) {
            listString.add(product.toString());
        }

        try {
            boolean status = fileManager.saveFile(FILE_NAME, listString);
            System.out.println(status ? "Save data successfully!" : "Failed to save data!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Product> loadFile() {
        IFileManager fileManager = new FileManager();

        List<Product> list = new ArrayList<>();
        try {
            List<String> dataLines = fileManager.loadFile(FILE_NAME);
            for (String dataLine : dataLines) {
                String[] fields = dataLine.split(",");
                if (fields.length == 6) {
                    String id = fields[0];
                    String name = fields[1];
                    Brand brand = brandList.getItem(fields[2]);
                    Category category = categoryList.getItem(fields[3]);
                    int year = Integer.parseInt(fields[4]);
                    int price = Integer.parseInt(fields[5]);
                    Product newProduct = new Product(year, price, brand, category, id, name);
                    if (newProduct != null) {
                        list.add(newProduct);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void openTable() {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("ID       Name         Year     Price   Brand    Category");
    }

    public void closeTable() {
        System.out.println("_______________________________________________________________________________________");
    }

    public void printListFromFile() {
        List<Product> listInFile = this.loadFile();
        if (listInFile.isEmpty()) {
            System.out.println("No Have Product");
            return;
        }
        this.openTable();
        for (Product product : listInFile) {
            String line = "";
            String data = product.printInfo();
            String[] fields = data.split(",");
            for (String field : fields) {
                line += field + "     ";
            }
            System.out.println(line.trim());
        }
        this.closeTable();
    }

    @Override
    public boolean checkExistId(String id) {
        return this.getItem(id.toUpperCase()) != null;
    }

}
