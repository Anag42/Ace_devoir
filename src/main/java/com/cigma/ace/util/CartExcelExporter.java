package com.cigma.ace.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.cigma.ace.model.Cart;
import com.cigma.ace.model.CartProduct;
import com.cigma.ace.model.Product;

public class CartExcelExporter {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Cart cart;
     
    public CartExcelExporter(Cart cart) {
        this.cart = cart;
        workbook = new XSSFWorkbook();
    }
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Ref", style);      
        createCell(row, 1, "Title", style);       
        createCell(row, 2, "Price", style);    
        createCell(row, 3, "Quantity", style);
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
    	int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(CartProduct cartProduct : cart.getCartProducts()){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            Product product = cartProduct.getProduct();
            createCell(row, columnCount++, product.getRef(), style);
            createCell(row, columnCount++, product.getTitle(), style);
            createCell(row, columnCount++, product.getPrice().toString(), style);
            createCell(row, columnCount++, cartProduct.getQuantity().toString(), style);            
        };
    }
     
    public byte[] generateFileAsBytes() throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        try {
            workbook.write(bos);
            workbook.close();
        } finally {
            bos.close();
        }
        
        byte[] excelFileAsBytes = bos.toByteArray();
       
        return excelFileAsBytes;
    }
}
