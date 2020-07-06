package com.example.demo.controller.clientsidetemplating.dto;

public class AchatDto {
    private int quantite;
    private long articleId;

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "AchatDto{" +
                "quantite=" + quantite +
                ", articleId=" + articleId +
                '}';
    }
}
