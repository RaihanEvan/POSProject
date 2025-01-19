package service;

import model.SaleItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<SaleItem> readSalesData(String filePath) {
        List<SaleItem> sales = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Flag to skip the header
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip the first line (header)
                    continue;
                }
                String[] data = line.split(",");
                String itemName = data[0];
                int quantity = Integer.parseInt(data[1].trim());
                double price = Double.parseDouble(data[2].trim());
                sales.add(new SaleItem(itemName, quantity, price));
            }
        } catch (Exception e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return sales;
    }
}
