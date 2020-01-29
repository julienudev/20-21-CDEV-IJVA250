package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    private EntityManager entityManager;

    public InitData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertTestData();
    }

    private void insertTestData() {
        Client client1 = newClient("Collet", "Adrienne", LocalDate.of(1979, 11, 6));
        entityManager.persist(client1);

        Client client2 = newClient("Brunet", "Valérie", LocalDate.of(1997, 6, 11));
        entityManager.persist(client2);

        entityManager.persist(newClient("Hardy", "Thierry-Eugène", LocalDate.of(1996, 5, 14)));
        entityManager.persist(newClient("ABSCHEN", "Jean", LocalDate.of(1996, 5, 14)));
        entityManager.persist(newClient("ADAMO", "Stéphane", LocalDate.of(19876, 5, 14)));
        entityManager.persist(newClient("AMELLAL", "Viviane", LocalDate.of(1996, 5, 14)));
        entityManager.persist(newClient("ANGONIN", "Jean-Pierre", LocalDate.of(1991, 5, 14)));
        entityManager.persist(newClient("AZOURA", "Marie-France", LocalDate.of(1996, 5, 14)));
        entityManager.persist(newClient("AZRIA", "Maryse", LocalDate.of(1996, 1, 14)));
        entityManager.persist(newClient("BACH", "Sylvie", LocalDate.of(1996, 5, 14)));
        entityManager.persist(newClient("BARNAUD", "Janine", LocalDate.of(1993, 5, 14)));
        entityManager.persist(newClient("BENSIMHON", "Pascal", LocalDate.of(1996, 5, 24)));
        entityManager.persist(newClient("BERTRAND", "Roger", LocalDate.of(1996, 5, 14)));
        entityManager.persist(newClient("BIDAULT", "Marie-Reine", LocalDate.of(1991, 5, 14)));
        entityManager.persist(newClient("BINET", "Emmanuel", LocalDate.of(1990, 5, 18)));
        entityManager.persist(newClient("BLANC", "Giséle", LocalDate.of(1999, 1, 14)));
        entityManager.persist(newClient("BLANCHOT", "Guy", LocalDate.of(1996, 1, 17)));
        entityManager.persist(newClient("BOUCHET", "Micheline", LocalDate.of(1996, 5, 14)));
        entityManager.persist(newClient("BOUDART", "Orianne", LocalDate.of(1993, 5, 15)));
        entityManager.persist(newClient("BOULLICAUD", "Paul", LocalDate.of(1995, 5, 14)));

        Article a1 = new Article();
        a1.setLibelle("Chargeurs de téléphones Portables");
        a1.setPrix(22.98);
        entityManager.persist(a1);

        Article a2 = new Article();
        a2.setLibelle("Playmobil Hydravion de Police");
        a2.setPrix(14.39);
        entityManager.persist(a2);

        Article a3 = new Article();
        a3.setLibelle("Distributeur de croquettes pour chien");
        a3.setPrix(12.99);
        entityManager.persist(a3);

        Facture f1 = new Facture();
        f1.setClient(client1);
        entityManager.persist(f1);
        entityManager.persist(newLigneFacture(f1, a1, 2));
        entityManager.persist(newLigneFacture(f1, a2, 1));

        Facture f2 = new Facture();
        f2.setClient(client2);
        entityManager.persist(f2);
        entityManager.persist(newLigneFacture(f2, a1, 10));

        Facture f3 = new Facture();
        f3.setClient(client1);
        entityManager.persist(f3);
        entityManager.persist(newLigneFacture(f3, a3, 1));

    }


    private LigneFacture newLigneFacture(Facture f, Article a1, int quantite) {
        LigneFacture lf1 = new LigneFacture();
        lf1.setArticle(a1);
        lf1.setQuantite(quantite);
        lf1.setFacture(f);
        return lf1;
    }


    private Client newClient(String nom, String prenom, LocalDate dateNaissance) {
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setDateNaissance(dateNaissance);
        return client;
    }

}
