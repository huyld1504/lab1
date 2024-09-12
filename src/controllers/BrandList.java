/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Brand;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import FileManager.IFileManager;

/**
 *
 * @author Asus
 */
public class BrandList implements IItemManager<Brand>, IFileManager{

    private List<Brand> brands;

    public BrandList() {
        this.brands = new ArrayList<>();
    }

    @Override
    public List<Brand> searchByName(String name) {
        List<Brand> result = new ArrayList<>();

        for (Brand brand : this.brands) {
            if (brand.getName().equalsIgnoreCase(name)) {
                result.add(brand);
            }
        }
        return result;
    }

    @Override
    public Brand getOne(String id) {
        Brand result = null;
        for (Brand brand : this.brands) {
            if (brand.getId().equals(id)) {
                result = brand;
            }
        }
        return result;
    }

    @Override
    public boolean add(Brand item) {
        return this.brands.add(item);
    }

    @Override
    public boolean update(String id, Brand params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean delete(String id) {
        Brand itemDelete = this.getOne(id);
        return this.brands.remove(itemDelete);
    }

    @Override
    public void saveFile() {
        PrintWriter w = null;
        try {
            File file = new File("brands.txt");
            if (file.exists()) {
                w = new PrintWriter("brands.txt");
                for (Brand brand : brands) {
                    String tmp = brand.getId() + "," + brand.getName() + "," + brand.getAddress() + "\n";
                    w.print(tmp);
                    w.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (w != null) {
                    w.close();
                }
                System.out.println("Save data successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void loadFile() {
        FileReader fReader = null;
        BufferedReader bReader = null;

        try {
            File file = new File("brands.txt");
            if (file.exists()) {
                fReader = new FileReader("brands.txt");
                bReader = new BufferedReader(fReader);

                while (bReader.ready()) {
                    String data = bReader.readLine();
                    String[] fields = data.split(",");

                    if (fields.length == 3) {
                        Brand brand = new Brand(fields[0].trim(), fields[1].trim(), fields[2].trim());
                        this.add(brand);
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

    @Override
    public boolean checkExistId(String id) {
        return this.getOne(id.toUpperCase()) != null;
    }

}
