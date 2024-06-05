package org.example;

import java.util.ArrayList;
import java.util.List;

public class Manche {
    private int score;
    private boolean derniereManche;
    private IGenerateur generateur;
    private List<Rouleau> rouleaux;

    public Manche(IGenerateur generateur, boolean derniereManche) {
        this.derniereManche = derniereManche;
        this.generateur = generateur;
        this.rouleaux = new ArrayList<>();
        this.score = 0;
    }

    public boolean lancer() {
        if (estLancerAutorise()) {
            int quilles = generateur.genererQuillesAleatoires(10);
            Rouleau rouleau = new Rouleau(quilles);
            rouleaux.add(rouleau);
            score += quilles;
            return true;
        }
        return false;
    }

    private boolean estLancerAutorise() {
        // Si ce n'est pas la dernière manche
        if (!derniereManche) {
            // Autorise le lancer si moins de 2 lancers ont été effectués ou si le score total est inférieur à 10 (pas de strike)
            return rouleaux.size() < 2 && getScore() < 10;
        } else {
            // Pour la dernière manche
            if (rouleaux.size() < 2) {
                return true; // Toujours autorisé pour les deux premiers lancers
            } else if (rouleaux.size() == 2) {
                // Autorise un troisième lancer si un strike ou un spare a été obtenu
                return rouleaux.get(0).getQuilles() == 10 || getScore() == 10;
            } else {
                return false; // Ne permet pas plus de trois lancers
            }
        }
    }

    public int getScore() {
        return score;
    }

    public List<Rouleau> getRouleaux() {
        return rouleaux;
    }
}

