/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import tool_utils.DataInput;
import tool_utils.DataValidation;
import business_objects.Product;
import data_objects.BrandList;
import data_objects.CategoryList;
import application.service.ProductService;
import data_objects.ProductList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class ProductMenu {

    final String FORMAT_PRODUCT_ID = "P\\d{3}";
    final String FORMAT_BRAND_ID = "B\\d{3}";
    final String FORMAT_CATEGORY_ID = "C\\d{3}";

    private ProductService services;

    public ProductMenu() {
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
        return DataValidation.isValidModelYear(y);
    }

    private boolean validateFormat(String str, String format) {
        return DataValidation.isMatchWithFormat(str, format);
    }

    //Main program to run system.
    public void runMenu() {
        boolean flag = true;
        ProductList listOfProductList = new ProductList();
        listOfProductList.loadFile();

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
                        flagOption = this.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                case 2:
                    System.out.println(">>>> SEARCH PRODUCT BY NAME <<<<");
                    while (!flagOption) {
                        this.printProductBySearchName();
                        flagOption = this.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                case 3:
                    System.out.println(">>>> UPDATE PRODUCT BY ID <<<<");
                    while (!flagOption) {
                        this.deleteProductById(listOfProductList);
                        flagOption = this.confirmYesOrNo("Go to menu (y/n): ");
                    }
                    break;
                case 4:
                    System.out.println(">>>> DELETE PRODUCT BY ID <<<<");
                    while (!flagOption) {
                        this.deleteProductById(listOfProductList);
                        flagOption = this.confirmYesOrNo("Go to menu (y/n): ");
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
                        flagOption = this.confirmYesOrNo("Go to menu (y/n): ");
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

    public boolean confirmYesOrNo(String message) {
        try {
            boolean flag = true;
            String option;
            while (flag) {
                option = DataInput.getString(message);
                if (option.equalsIgnoreCase("y")) {
                    flag = true;
                    break;
                } else if (option.equalsIgnoreCase("n")) {
                    flag = false;
                    break;
                }
            }
            return flag;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Product createProduct() {
        Product result = null;
        ProductList productList = new ProductList();
        productList.loadFile();
        BrandList brandList = new BrandList();
        brandList.loadFile();
        CategoryList categoryList = new CategoryList();
        categoryList.loadFile();

        try {
            String id = DataInput.getString(">> Enter id: ").toUpperCase();
            while (productList.checkExistId(id)) {
                id = DataInput.getString(">> The above ID existed, please enter again: ");
            }
            String name = DataInput.getString(">> Enter name: ");
            String brandId = DataInput.getString(">> Enter brand id: ").toUpperCase();
            while (!brandList.checkExistId(brandId)) {
                brandId = DataInput.getString(">> Brand not found, please enter again: ").toUpperCase();
            }
            String categoryId = DataInput.getString(">> Enter category id: ").toUpperCase();
            while (!categoryList.checkExistId(categoryId)) {
                categoryId = DataInput.getString(">> Category not found, please enter again: ").toUpperCase();
            }
            int modelYear, listPrice;
            modelYear = DataInput.getInt(">> Enter year: ");
            listPrice = DataInput.getInt(">> Enter price: ");

            //Validate data
            boolean isValidData = !validateString(id)
                    && !validateString(name)
                    && !validateString(brandId)
                    && !validateString(categoryId)
                    && validateYear(modelYear)
                    && validatePositiveInt(listPrice)
                    && validateFormat(id, FORMAT_PRODUCT_ID)
                    && validateFormat(brandId, FORMAT_BRAND_ID)
                    && validateFormat(categoryId, FORMAT_CATEGORY_ID);

            if (isValidData) {
                result = new Product(modelYear, listPrice, brandList.getOne(brandId), categoryList.getOne(categoryId), id, name);
            } else {
                System.out.println("Invalid Data");
            }

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void printProductBySearchName() {
        ProductList productList = new ProductList();
        productList.loadFile();
        try {
            String params = DataInput.getString("Enter the name you want to search: ");
            List<Product> listOfProductSearchByName = productList.searchByName(params);
            if (listOfProductSearchByName.isEmpty()) {
                System.out.println("Have no any Product");
            } else {
                listOfProductSearchByName.stream().forEach(p -> {
                    System.out.println(p.printInfoProduct());
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProductById(ProductList productList) {
        try {
            String deleteId = DataInput.getString("Enter the product's id: ");
            boolean confirmDelete = this.confirmYesOrNo("Do you want to delete this product? (y/n) ");
            if (confirmDelete) {
                System.out.println(productList.delete(deleteId) ? "Deleted product successfully!" : "Failed to delete!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateProductById(ProductList productList) {
        BrandList brandList = new BrandList();
        brandList.loadFile();
        CategoryList categoryList = new CategoryList();
        categoryList.loadFile();

        try {
            String idUpdate = DataInput.getString("Enter the product's id to update: ");
            Product oldProduct = productList.getOne(idUpdate);
            if (oldProduct != null) {
                // form fill the update infomation
                String name = DataInput.getNewString("Enter new name: ", oldProduct.getName());
                String brandId = DataInput.getNewString("Enter new brand id: ", oldProduct.getBrand().getId()).toUpperCase();
                while (!brandList.checkExistId(brandId)) {
                    brandId = DataInput.getNewString("Brand id does not exist, please enter again: ", oldProduct.getBrand().getId()).toUpperCase();
                }
                String categoryId = DataInput.getNewString("Enter category id: ", oldProduct.getCategory().getId()).toUpperCase();
                while (!categoryList.checkExistId(categoryId)) {
                    categoryId = DataInput.getNewString("Category does not exist, please enter again", oldProduct.getCategory().getId()).toUpperCase();
                }
                int modelYear = DataInput.getNewInt("Enter new model year: ", oldProduct.getModelYear());
                int listPrice = DataInput.getNewInt("Enter new price: ", oldProduct.getListPrice());

                boolean isValidData = validateFormat(brandId, FORMAT_BRAND_ID) && validateFormat(categoryId, FORMAT_CATEGORY_ID);
                if (isValidData) {
                    Product newProduct = new Product(modelYear, listPrice, brandList.getOne(brandId), categoryList.getOne(categoryId), oldProduct.getId(), name);
                    System.out.println(productList.update(oldProduct.getId(), newProduct) ? "Updated" : "Failed");
                } else {
                    System.out.println("Invalid Data");
                }
            } else {
                System.out.println("Product does not exists!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
