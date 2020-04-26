package com.example.demo.controller.export;

import com.example.demo.entity.Facture;
import com.example.demo.service.ExportPDFITextService;
import com.example.demo.service.FactureExportXLSX;
import com.example.demo.service.FactureService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller pour réaliser export des factures.
 */
@Controller
@RequestMapping("export")
public class ExportFactureController {

    @Autowired
    private FactureService factureService;

    @Autowired
    private FactureExportXLSX facturesExportXLSX;

    @Autowired
    private ExportPDFITextService exportPDFITextService;

    /**
     * Export des factures d'un client au format XLSX.
     */
    @GetMapping("/clients/{id}/factures/xlsx")
    public void clientGetFacturesXLSX(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"client-" + id + "-factures.xlsx\"");
        facturesExportXLSX.factureXLSX(id, response.getOutputStream());
    }


    /**
     * Export de toutes les factures de l'application au format CSV.
     */
    @GetMapping("/factures/xlsx")
    public void facturesXLSX(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"factures.xlsx\"");
        facturesExportXLSX.factureXLSX(response.getOutputStream());
    }

    /**
     * Export d'une facture au format PDF.
     */
    @GetMapping("/factures/{id}/pdf")
    public void facturesPDF(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws IOException, DocumentException {
        Facture facture = factureService.findById(id);
        exportPDFITextService.export(response.getOutputStream(), facture);
    }
}
