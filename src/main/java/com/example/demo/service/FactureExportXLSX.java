package com.example.demo.service;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Service
public class FactureExportXLSX {

    @Autowired
    private ClientService clientService;
    @Autowired
    private FactureService factureService;


    public void factureXLSX(ServletOutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        for (Client client : clientService.findAllClients()) {
            int nbFactures = client.getFactures().size();
            if (nbFactures > 0) {
                Sheet sheetClient = workbook.createSheet(client.getNom() + " " + client.getPrenom());
                Row rowNom = sheetClient.createRow(0);
                Cell cellHeaderNom = rowNom.createCell(0);
                cellHeaderNom.setCellValue("Nom");

                Cell cellNom = rowNom.createCell(1);
                cellNom.setCellValue(client.getNom());

                Row rowIdFactures = sheetClient.createRow(3);
                Cell cellFacture = rowIdFactures.createCell(0);
                cellFacture.setCellValue(nbFactures + " facture(s) :");
                int iColIdFacture = 1;
                for (Facture facture : client.getFactures()) {
                    Cell cellFactureId = rowIdFactures.createCell(iColIdFacture++);
                    cellFactureId.setCellValue(facture.getId());
                }

                for (Facture facture : client.getFactures()) {
                    createSheetFacture(workbook, facture);
                }
            }
            //...
        }

        //...
        workbook.write(outputStream);
        workbook.close();
    }

    private void createSheetFacture(Workbook workbook, Facture facture) {
        Sheet sheetFacture = workbook.createSheet("Facture n " + facture.getId());

        Row rowHeader = sheetFacture.createRow(0);
        Cell cellHeaderDesignation = rowHeader.createCell(0);
        cellHeaderDesignation.setCellValue("Désignation");
        Cell cellHeaderQte = rowHeader.createCell(1);
        cellHeaderQte.setCellValue("Quantité");
        Cell cellHeaderPrixUnit = rowHeader.createCell(2);
        cellHeaderPrixUnit.setCellValue("Prix unitaire");
        //..
        int iRow = 1;
        for (LigneFacture ligneFacture : facture.getLigneFactures()) {
            Row row = sheetFacture.createRow(iRow++);
            Cell cellDesignation = row.createCell(0);
            cellDesignation.setCellValue(ligneFacture.getArticle().getLibelle());

            Cell cellQte = row.createCell(1);
            cellQte.setCellValue(ligneFacture.getQuantite());

            Cell cellPrixUnit = row.createCell(2);
            cellPrixUnit.setCellValue(ligneFacture.getArticle().getPrix());
        }

        //total calculer le tolta de la facture
        int iRowTotal = iRow++;
        Row rowTotal = sheetFacture.createRow(iRowTotal);
        Cell cellTotalLibelle = rowTotal.createCell(0);
        cellTotalLibelle.setCellValue("TOTAL");

        sheetFacture.addMergedRegion(new CellRangeAddress(iRowTotal, iRowTotal, 0, 1));

        CellStyle styleTotalLibelle = workbook.createCellStyle();
        styleTotalLibelle.setAlignment(HorizontalAlignment.RIGHT);
        Font fontTotalLibelle = workbook.createFont();
        fontTotalLibelle.setBold(true);
        styleTotalLibelle.setFont(fontTotalLibelle);
        cellTotalLibelle.setCellStyle(styleTotalLibelle);

        Cell cellTotalValue = rowTotal.createCell(2);
        cellTotalValue.setCellValue(facture.getTotal());

    }

}
