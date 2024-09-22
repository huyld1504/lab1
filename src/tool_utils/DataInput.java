/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool_utils;

import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class DataInput {

    public static final String FORMAT_NUMBER = "^-?\\d+$";

    public static Scanner openScanner() {
        return new Scanner(System.in);
    }

    public static int getInt(String message) throws Exception {
        Scanner sc = openScanner();
        System.out.print(message);
        String input = sc.nextLine();

        if (!input.matches(FORMAT_NUMBER)) {
            throw new Exception("Invalid data");
        } else {
            return Integer.parseInt(input);
        }
    }

    public static String getString(String message) throws Exception {
        Scanner sc = openScanner();
        System.out.print(message);
        String input = sc.nextLine();

        if (input.trim().equals("")) {
            throw new Exception("The field is not empty");
        } else {
            return input;
        }
    }

    public static String getString(String message, String oldData) throws Exception {
        Scanner sc = openScanner();
        System.out.print(message);
        String input = sc.nextLine();

        if (input.trim().equals("")) {
            input = oldData;
        }
        return input;
    }

    public static int getInt(String message, int oldData) throws Exception {
        Scanner sc = openScanner();
        System.out.print(message);
        String input = sc.nextLine();

        if (!input.trim().matches(FORMAT_NUMBER)) {
            return oldData;
        }

        return Integer.parseInt(input);
    }

    public static boolean confirmYesOrNo(String message) {
        try {
            boolean flag = true;
            String option;
            while (flag) {
                option = DataInput.getString(message, "a");
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
}
