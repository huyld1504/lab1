/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_objects;

/**
 *
 * @author Asus
 */
public class Brand extends Item{
    private String address;

    public Brand(String id, String name, String address) {
        super(id, name);
        this.address = address;
    }
    
    public String getAddress () {
        return this.address;
    }   
}
