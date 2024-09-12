/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;

/**
 *
 * @author Asus
 * @param <Item>
 */
public interface IItemManager<Item> {
   public List<Item> searchByName(String name);
   public Item getOne(String id);
   public boolean add(Item item);
   public boolean update(String id, Item params);
   public boolean delete(String id);
//   public void saveFile();
//   public void loadFile();
   public boolean checkExistId(String id);
}
