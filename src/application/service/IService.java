/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import java.util.List;

/**
 *
 * @author Asus
 * @param <T>
 */
public interface IService<T> {
    void printService();
    boolean addService(T obj);
    List<T> getList();
}