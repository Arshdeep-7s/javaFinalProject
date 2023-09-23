package com.example.project1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Account {
    public static void initializeAccountFile() {
        String fileName = "accounts.txt";
        if (!Files.exists(Paths.get(fileName))) {
            try {
                Files.createFile(Paths.get(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean validateLogin(String username, String password, String userType) {
        ArrayList<String[]> accounts = readAccountsFile();
        for (String[] parts : accounts) {
            if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password) && parts[2].equals(userType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateAccountCreation(String username, String password, String confirmPassword, String userType) {
        // Password validation (no changes)
        if (password.isEmpty() || confirmPassword.isEmpty() || !password.equals(confirmPassword)) {
            return false;
        }

        // Username validation (no changes)
        if (!username.matches("^[a-zA-Z0-9_-]{3,20}$")) {
            return false;
        }

        ArrayList<String[]> accounts = readAccountsFile();
        for (String[] parts : accounts) {
            if (parts.length >= 2 && parts[0].equals(username)) {
                return false; // Username already exists
            }
        }
        return true;
    }

    public static void saveAccountData(String username, String password, String userType) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            String accountInfo = username + "," + password + "," + userType;
            bw.write(accountInfo);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String[]> readAccountsFile() {
        ArrayList<String[]> accounts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                accounts.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
