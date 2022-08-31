package ru.gb.market.services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import ru.gb.market.models.Product;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileService {

    private final String ROOT_DIR = ".//files/";
    private final ProductService productService;

    private final String[] priceHeaders = {"ID", "Title", "Cost", "Category", "Availability"};

    public FileService(ProductService productService) throws IOException {
        this.productService = productService;
        Path dir = Path.of(ROOT_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }
    }

    public String generatePrice() throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Price");
        List<Product> plist = productService.findAll();
        Collections.sort(plist, Comparator.comparing(Product::getId));
        Row titleRow = sheet.createRow(0);
        for (int i = 0; i < priceHeaders.length; i++) {
            titleRow.createCell(i).setCellValue(priceHeaders[i]);
        }
        for (Product product : plist) {
            Row row = sheet.createRow(plist.indexOf(product) + 1);
            row.createCell(0).setCellValue(product.getId().toString());
            row.createCell(1).setCellValue(product.getTitle());
            row.createCell(2).setCellValue(product.getCost().toString());
            row.createCell(3).setCellValue(product.getCategory().getTitle());
            row.createCell(4).setCellValue("AVAILABLE"); //заглушка проверки наличия на складе
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
        String fileName = "Price_" + formatter.format(new Date()) + ".xls";
        FileOutputStream out = new FileOutputStream(ROOT_DIR + fileName);
        workbook.write(out);
        out.close();
        return fileName;
    }

    public byte[] getPriceFile(String fileName) throws IOException {
        return Files.readAllBytes(Path.of(ROOT_DIR + fileName));
    }

}
