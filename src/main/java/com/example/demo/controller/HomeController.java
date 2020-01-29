package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Controller principale pour affichage des clients / factures sur la page d'acceuil.
 */
@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private FactureService factureService;

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");

        List<Article> articles = articleService.findAll();
        modelAndView.addObject("articles", articles);

        List<Client> toto = clientService.findAllClients();
        modelAndView.addObject("clients", toto);

        List<Facture> factures = factureService.findAllFactures();
        modelAndView.addObject("factures", factures);

        return modelAndView;
    }


    @GetMapping("/articles/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");
        PrintWriter writer = response.getWriter();

        writer.println("Libelle;Prix");
        List<Article> articles = articleService.findAll();
        for (Article article : articles) {
            String line = article.getLibelle() + ";" + article.getPrix();
            writer.println(line);
        }
    }

    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        PrintWriter writer = response.getWriter();

        writer.println("Nom;Prénom;Age");
        List<Client> clients = clientService.findAllClients();
        for (Client client : clients) {
            String line = client.getNom() + ";" + client.getPrenom() + "?TODO?";
            writer.println(line);
        }
    }

    @GetMapping("/clients/xlsx")
    public void clientsXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"clients.xlsx\"");

        Workbook workbook = new XSSFWorkbook();
        //Apache POI

        //...
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    @GetMapping("/clients/pdf")
    public void clientsPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO cf github dans la soirée.

    }
}
