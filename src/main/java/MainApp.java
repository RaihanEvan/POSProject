
import model.SaleItem;
import service.CSVReader;
import service.PDFGenerator;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        String csvPath = "src/main/resources/sales.csv";
        String pdfPath = "src/main/resources/SalesReport.pdf";
        String logoPath = "src/main/resources/logo.png";

        List<SaleItem> sales = CSVReader.readSalesData(csvPath);

        PDFGenerator.generateSalesReport(sales, pdfPath, logoPath);
    }
}
