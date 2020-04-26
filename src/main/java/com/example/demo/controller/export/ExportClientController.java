package com.example.demo.controller.export;

import com.example.demo.entity.Client;
import com.example.demo.service.ClientExportXLSX;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller pour réaliser export des clients.
 */
@Controller
@RequestMapping("export")
public class ExportClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientExportXLSX clientExportXLSX;

    /**
     * Export des clients au format CSV.
     */
    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        PrintWriter writer = response.getWriter();

        writer.println("Nom;Prénom;Age");
        List<Client> clients = clientService.findAllClients();
        for (Client client : clients) {
            int age = LocalDate.now().getYear() - client.getDateNaissance().getYear();
            String line = client.getNom() + ";" + client.getPrenom() + ";" + age;
            writer.println(line);
        }
    }

    /**
     * Export des clients au format XLSX.
     */
    @GetMapping("/clients/xlsx")
    public void clientsXLSX(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"clients.xlsx\"");
        clientExportXLSX.clientsXLSX(response.getOutputStream());
    }

}
