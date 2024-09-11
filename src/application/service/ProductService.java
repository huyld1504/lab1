/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class ProductService implements IService<String>{
    private List<String> services;

    public ProductService() {
        this.services = new ArrayList<>();
    }
    
    @Override
    public void printService() {
        for (String service : services) {
            System.out.println(service);
        }
    }

    @Override
    public List<String> getList() {
        return this.services;
    }

    @Override
    public boolean addService(String obj) {
        return this.services.add(obj);
    }

}
