
import model.SaleItem;
import service.CSVReader;
import service.PDFGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.util.List;

public class MainApp extends JFrame {

    private JTable salesTable;
    private DefaultTableModel tableModel;

    private static final String CSV_PATH = "src/resources/sales.csv";
    private static final String PDF_PATH = "src/resources/SalesReport.pdf";
    private static final String LOGO_PATH = "src/resources/logo.png";

    public MainApp() {
        setTitle("POS System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table for displaying sales data
        tableModel = new DefaultTableModel(new String[]{"Item Name", "Quantity", "Price"}, 0);
        salesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons and input
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));

        // Input fields for adding sales data
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Item Name:"));
        JTextField itemNameField = new JTextField(10);
        inputPanel.add(itemNameField);

        inputPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField(5);
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField(7);
        inputPanel.add(priceField);

        JButton addButton = new JButton("Add Data");
        inputPanel.add(addButton);
        controlPanel.add(inputPanel);

        // Buttons for other actions
        JButton generatePdfButton = new JButton("Generate PDF Report");
        JButton loadSalesButton = new JButton("Load Sales Data");
        JButton clearTableButton = new JButton("Clear Table");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generatePdfButton);
        buttonPanel.add(loadSalesButton);
        buttonPanel.add(clearTableButton);
        controlPanel.add(buttonPanel);

        add(controlPanel, BorderLayout.SOUTH);

        // Button Action: Add Data
        addButton.addActionListener(e -> {
            try {
                String itemName = itemNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                SaleItem saleItem = new SaleItem(itemName, quantity, price);

                // Append to CSV
                try (FileWriter writer = new FileWriter(CSV_PATH, true)) {
                    writer.write(saleItem.toString() + "\n");
                }

                // Add to table
                tableModel.addRow(new Object[]{itemName, quantity, price});
                JOptionPane.showMessageDialog(this, "Data added successfully!");
                itemNameField.setText("");
                quantityField.setText("");
                priceField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding data: " + ex.getMessage());
            }
        });

        // Button Action: Generate PDF
        generatePdfButton.addActionListener(e -> {
            try {
                List<SaleItem> sales = CSVReader.readSalesData(CSV_PATH);
                PDFGenerator.generateSalesReport(sales, PDF_PATH, LOGO_PATH);
                JOptionPane.showMessageDialog(this, "PDF report generated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating PDF: " + ex.getMessage());
            }
        });

        // Button Action: Load Sales Data
        loadSalesButton.addActionListener(e -> {
            try {
                List<SaleItem> sales = CSVReader.readSalesData(CSV_PATH);
                tableModel.setRowCount(0); // Clear existing rows
                for (SaleItem sale : sales) {
                    tableModel.addRow(new Object[]{sale.getItemName(), sale.getQuantity(), sale.getPrice()});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading sales data: " + ex.getMessage());
            }
        });

        // Button Action: Clear Table
        clearTableButton.addActionListener(e -> tableModel.setRowCount(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}
