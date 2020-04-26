package com.example.demo.controller.clientsidetemplating.dto;

public class LigneFactureDto {
    public ArticleDto article;
    public int quantite;

    public LigneFactureDto(ArticleDto article, int quantite) {
        this.article = article;
        this.quantite = quantite;
    }
}
