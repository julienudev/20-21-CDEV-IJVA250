package com.example.demo.controller.clientsidetemplating;

import com.example.demo.controller.clientsidetemplating.dto.AchatDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller pour exposition d'api REST des articles, utilisé dans le cas d'une application client side templating.
 */
@RestController
@RequestMapping("rest/acheter")
public class AcheterRestController {

    /**
     * Exposition d'une api déclenchée sur l'url http://..../articles.
     *
     * @return la liste des articles au format JSON. Voir la classe ArticleDto pour le détail du format.
     */
    @PostMapping
    public void acheter(@RequestBody List<AchatDto> achats) {
        System.out.println(achats);
    }

}
