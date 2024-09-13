/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Product;
import models.Brand;
import models.Category;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import filemanager.IFileManager;

/**
 *
 * @author Asus
 */
public class ProductList implements IItemManager<Product>, IFileManager {

    private List<Product> list;

    public ProductList() {
        this.list = new ArrayList<>();
    }
    
    public List<Product> getList () {
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
        return result;
    }

    @Override
    public Product getOne(String id) {
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
        if (item == null) return false;
        return this.list.add(item);
    }

    @Override
    public boolean update(String id, Product params) {
        if (params == null) return false;
        if (!this.checkExistId(id)) return false;
        Product oldProduct = this.getOne(id);
        
        int position = this.list.indexOf(oldProduct);
        if (position == -1) return false;
        
        this.list.set(position, params);       
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (!this.checkExistId(id)) return false;
        return this.list.remove(this.getOne(id));
    }

    @Override
    public void saveFile() {
        PrintWriter pWriter = null;
        String fileName = "products.txt";

        try {
            File file = new File(fileName);
            if (file.exists()) {
                pWriter = new PrintWriter(fileName);
                for (Product product : this.list) {
                    String tmp = product.getId() + "," + product.getName() + "," + product.getBrand().getId().toUpperCase() + "," + product.getCategory().getId().toUpperCase() + "," + product.getModelYear() + "," + product.getListPrice() + "\n";
                    pWriter.print(tmp);
                    pWriter.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (pWriter != null) {
                pWriter.close();
            }
            System.out.println("Save data successfully");
        }
    }

    @Override
    public void loadFile() {
        FileReader fReader = null;
        BufferedReader bReader = null;

        try {
            File file = new File("products.txt");
            if (file.exists()) {
                fReader = new FileReader("products.txt");
                bReader = new BufferedReader(fReader);

                while (bReader.ready()) {
                    String dataLine = bReader.readLine();
                    String[] fields = dataLine.split(",");

                    BrandList listOfBrand = new BrandList();
                    listOfBrand.loadFile();

                    CategoryList listOfCategory = new CategoryList();
                    listOfCategory.loadFile();

                    if (fields.length == 6) {
                        String id = fields[0];
                        String name = fields[1];
                        Brand brand = listOfBrand.getOne(fields[2]);
                        Category category = listOfCategory.getOne(fields[3]);
                        int modelYear = Integer.parseInt(fields[4]);
                        int listPrice = Integer.parseInt(fields[5]);

                        Product newProduct = new Product(modelYear, listPrice, brand, category, id, name);
                        this.list.add(newProduct);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fReader != null) {
                    fReader.close();
                }
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void openTable () {
        System.out.println("_______________________________________________________________________________________");
                System.out.println("ID          Name            Brand name  Category name        Year        Price ");
    }
    
    public void closeTable () {
        System.out.println("_______________________________________________________________________________________");
    }

    public void printListFromFile() {
        this.openTable();
        for (Product product : list) {
            String line = "";
            String[] fields = product.toString().split(",");
            for (String field : fields) {
                line += field + "        ";
            }
            System.out.println(line);
        }
        this.closeTable();
    }

    @Override
    public boolean checkExistId(String id) {
        return this.getOne(id.toUpperCase()) != null;
    }

}
