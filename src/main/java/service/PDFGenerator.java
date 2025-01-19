package service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.SaleItem;

import com.itextpdf.text.Document;
//import javax.swing.text.Document;
import java.io.FileOutputStream;
import java.util.List;

public class PDFGenerator {

    public static void generateSalesReport(List<SaleItem> sales, String filePath, String logoPath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add logo
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            document.add(logo);

            // Add header
            Paragraph header = new Paragraph("Sales Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" ")); // Spacer

            // Add table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Item Name");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Total Price");

            double grandTotal = 0.0;
            for (SaleItem item : sales) {
                table.addCell(item.getItemName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getPrice()));
                table.addCell(String.valueOf(item.getTotalPrice()));
                grandTotal += item.getTotalPrice();
            }

            document.add(table);

            // Add total
            document.add(new Paragraph("Grand Total: " + grandTotal, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));

            document.close();
            System.out.println("PDF generated successfully.");
        } catch (Exception e) {
            System.out.println("Error generating PDF: " + e.getMessage());
        }
    }
}
