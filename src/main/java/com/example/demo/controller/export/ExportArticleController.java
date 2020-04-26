package com.example.demo.controller.export;

import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Controller pour réaliser export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * Export des articles au format CSV.
     */
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

}
