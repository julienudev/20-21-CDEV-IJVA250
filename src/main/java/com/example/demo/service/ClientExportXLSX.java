package com.example.demo.service;

import com.example.demo.entity.Client;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class ClientExportXLSX {

    @Autowired
    private ClientService clientService;

    public static final int COL_NOM = 0;
    public static final int COL_PRENOM = 1;
    public static final int COL_AGE = 2;

    public void clientsXLSX(ServletOutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        //Apache POI
        Sheet sheet = workbook.createSheet("Clients");
        Row headerRow = sheet.createRow(0);

        CellStyle styleHeader = styleColor(workbook);

        Cell cellHeaderNom = headerRow.createCell(COL_NOM);
        cellHeaderNom.setCellValue("Nom");
        cellHeaderNom.setCellStyle(styleHeader);

        Cell cellHeaderPrenom = headerRow.createCell(COL_PRENOM);
        cellHeaderPrenom.setCellValue("Prénom");
        cellHeaderPrenom.setCellStyle(styleHeader);

        Cell cellHeaderAge = headerRow.createCell(COL_AGE);
        cellHeaderAge.setCellValue("Age");
        cellHeaderAge.setCellStyle(styleHeader);

        CellStyle cellStyleBorder = newStyleBorder(workbook);

        int iRow = 1;
        for (Client client : clientService.findAllClients()) {
            Row row = sheet.createRow(iRow++);

            Cell cellNom = row.createCell(COL_NOM);
            cellNom.setCellValue(client.getNom());
            cellNom.setCellStyle(cellStyleBorder);

            Cell cellPrenom = row.createCell(COL_PRENOM);
            cellPrenom.setCellValue(client.getPrenom());
            cellPrenom.setCellStyle(cellStyleBorder);

            int age = client.getDateNaissance().until(LocalDate.now()).getYears();
            Cell cellAge = row.createCell(COL_AGE);
            cellAge.setCellValue(age + " ans");
            cellAge.setCellStyle(cellStyleBorder);
        }


        //...
        workbook.write(outputStream);
        workbook.close();

    }

    private CellStyle styleColor(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        setBorderColor(style);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.PINK.getIndex());
        style.setFont(font);
        return style;
    }

    private CellStyle newStyleBorder(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        setBorderColor(style);
        return style;
    }

    private void setBorderColor(CellStyle style) {
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLUE.getIndex());

        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor(IndexedColors.BLUE.getIndex());

        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLUE.getIndex());

        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
    }


}
