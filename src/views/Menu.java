/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import tool_utils.DataInput;
import tool_utils.DataValidation;
import models.Product;
import controllers.BrandList;
import controllers.CategoryList;
import application.service.ProductService;
import controllers.ProductList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Menu {

    private static final String FORMAT_PRODUCT_ID = "[p | P]\\d{3}";
    private static final String FORMAT_BRAND_ID = "[B | b]\\d{3}";
    private static final String FORMAT_CATEGORY_ID = "[C | c]\\d{3}";

    private ProductService services;

    public Menu() {
        this.services = new ProductService();
        this.services.addService("1. Add product");
        this.services.addService("2. Search product by product name");
        this.services.addService("3. Update product");
        this.services.addService("4. Delete product");
        this.services.addService("5. Save products to file");
        this.services.addService("6. Print list of products from file");
        this.services.addService(">> Other: Quit program");
    }

    private boolean validateString(String str) {
        return DataValidation.isEmptyString(str);
    }

    private boolean validatePositiveInt(int n) {
        return DataValidation.isPositiveNumber(n);
    }

    private boolean validateYear(int y) {
        return DataValidation.isValidModelYear(y) && DataValidation.isPositiveNumber(y);
    }

    private boolean validateFormat(String str, String format) {
        return DataValidation.isMatchWithFormat(str, format);
    }

    //Main program to run system.
    public void runMenu() {
        boolean flag = true;
        ProductList listOfProductList = new ProductList();

        System.out.println("********** PRODUCT MANAGEMENT **********");
        do {
            this.printMenu();
            int option = this.getChoice();
            boolean flagOption = false;

            switch (option) {
                case 1:
                    System.out.println(">>>> ADD PRODUCT <<<<");
                    while (!flagOption) {
                        Product newProduct = this.createProduct();
                        System.out.println(newProduct != null && listOfProductList.add(newProduct) ? "Added successfully!" : "Failed to add!");
                        flagOption = DataInput.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                case 2:
                    System.out.println(">>>> SEARCH PRODUCT BY NAME <<<<");
                    while (!flagOption) {
                        this.printProductBySearchName();
                        flagOption = DataInput.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                case 3:
                    System.out.println(">>>> UPDATE PRODUCT BY ID <<<<");
                    while (!flagOption) {
                        this.updateProductById(listOfProductList);
                        flagOption = DataInput.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                case 4:
                    System.out.println(">>>> DELETE PRODUCT BY ID <<<<");
                    while (!flagOption) {
                        this.deleteProductById(listOfProductList);
                        flagOption = DataInput.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                case 5:
                    System.out.println(">>>> SAVE DATA <<<<");
                    listOfProductList.saveFile();
                    break;
                case 6:
                    System.out.println(">>>> LIST OF PRODUCT FROM FILE <<<<");
                    while (!flagOption) {
                        listOfProductList.printListFromFile();
                        flagOption = DataInput.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                default:
                    System.out.println("Good bye!!!");
                    flag = !flag;
            }
        } while (flag);
    }

    public void printMenu() {
        this.services.getList().stream().forEach(service -> System.out.println(service));
    }

    public int getChoice() {
        int number = -1;
        try {
            number = DataInput.getInt(">> Select choice: ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return number;
    }

    public Product createProduct() {
        Product result;
        ProductList productList = new ProductList();
        BrandList brandList = productList.getBrandList();
        CategoryList categoryList = productList.getCategoryList();

        try {
            String validFieldModelYear = "This field is invalid, please enter again: ";

            // Product ID
            String id = DataInput.getString(">> Enter id: ").toUpperCase();
            while (productList.checkExistId(id)) {
                id = DataInput.getString(">> The product ID existed, please enter again: ");
            }
            while (!validateFormat(id, FORMAT_PRODUCT_ID)) {
                System.out.println("Format not match (Pxxx with x is a number)");
                id = DataInput.getString("Please enter again: ");
            }

            //Product name
            String name = DataInput.getString(">> Enter name: ");

            //Brand
            String brandId = DataInput.getString(">> Enter brand id: ").toUpperCase();
            while (!brandList.checkExistId(brandId)) {
                brandList.printList();
                brandId = DataInput.getString(">> Brand not found, please enter again: ").toUpperCase();
            }
            while (!validateFormat(brandId, FORMAT_BRAND_ID)) {
                System.out.println("Format not match (Bxxx with x is a number)");
                id = DataInput.getString("Please enter again: ");
            }

            //Category
            String categoryId = DataInput.getString(">> Enter category id: ").toUpperCase();
            while (!categoryList.checkExistId(categoryId)) {
                categoryList.printList();
                categoryId = DataInput.getString(">> Category not found, please enter again: ").toUpperCase();
            }
            while (!validateFormat(categoryId, FORMAT_CATEGORY_ID)) {
                System.out.println("Format not match (Cxxx with x is a number)");
                id = DataInput.getString("Please enter again: ");
            }

            //Model year and list price
            int modelYear, listPrice;
            modelYear = DataInput.getInt(">> Enter year: ");
            while (!validateYear(modelYear)) {
                modelYear = DataInput.getInt(validFieldModelYear);
            }
            listPrice = DataInput.getInt(">> Enter price: ");
            while (!validatePositiveInt(listPrice)) {
                listPrice = DataInput.getInt("Price is a positive number, please enter again: ");
            }

            //create new product
            result = new Product(modelYear, listPrice, brandList.getItem(brandId), categoryList.getItem(categoryId), id, name);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void printProductBySearchName() {
        ProductList productList = new ProductList();
        try {
            String params = DataInput.getString("Enter the name you want to search: ");
            List<Product> listOfProductSearchByName = productList.searchByName(params);
            if (listOfProductSearchByName.isEmpty()) {
                System.out.println("Have no any Product");
            } else {
                listOfProductSearchByName.stream().forEach(p -> {
                    System.out.println(p.printInfo());
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProductById(ProductList productList) {
        try {
            String deleteId = DataInput.getString("Enter the product's id: ");
            boolean confirmDelete = DataInput.confirmYesOrNo("Do you want to delete this product? (y/n) ");
            if (confirmDelete) {
                System.out.println(productList.delete(deleteId) ? "Deleted product successfully!" : "Failed to delete!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateProductById(ProductList productList) {
        BrandList brandList = productList.getBrandList();
        CategoryList categoryList = productList.getCategoryList();
        
        try {
            String idUpdate = DataInput.getString("Enter the product's id to update: ");
            Product oldProduct = productList.getItem(idUpdate);
            if (oldProduct != null) {
                // form fill the update infomation
                String name = DataInput.getString("Enter new name: ", oldProduct.getName());
                String brandId = DataInput.getString("Enter new brand id: ", oldProduct.getBrand().getId()).toUpperCase();
                while (!brandList.checkExistId(brandId)) {
                    brandList.printList();
                    brandId = DataInput.getString("Brand id does not exist, please enter again: ", oldProduct.getBrand().getId()).toUpperCase();
                }
                String categoryId = DataInput.getString("Enter category id: ", oldProduct.getCategory().getId()).toUpperCase();
                while (!categoryList.checkExistId(categoryId)) {
                    categoryList.printList();
                    categoryId = DataInput.getString("Category does not exist, please enter again: ", oldProduct.getCategory().getId()).toUpperCase();
                }
                int modelYear = DataInput.getInt("Enter new model year: ", oldProduct.getModelYear());
                int listPrice = DataInput.getInt("Enter new price: ", oldProduct.getListPrice());

                boolean isValidData = validateFormat(brandId, FORMAT_BRAND_ID) && validateFormat(categoryId, FORMAT_CATEGORY_ID);
                if (isValidData) {
                    Product newProduct = new Product(modelYear, listPrice, brandList.getItem(brandId), categoryList.getItem(categoryId), oldProduct.getId(), name);
                    boolean confirmUpdate = DataInput.confirmYesOrNo("Do you want to update this product?(y/n) ");
                    if (confirmUpdate) {
                        System.out.println(productList.update(oldProduct.getId(), newProduct) ? "Updated" : "Failed");
                    }
                } else {
                    System.out.println("Invalid Format");
                    
                }
            } else {
                System.out.println("Product does not exists!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
