package main;

import javax.swing.*;
import java.awt.*;

public class PanneauDeJeu extends JPanel implements Runnable{

    public static final int LARGEUR = 1100;
    public static final int HAUTEUR = 800;
    final int FPS = 60;  //Nombre d'images par seconde
    Thread threadDeJeu;
    Echiquier echiquier = new Echiquier();


    public PanneauDeJeu() {
        setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        setBackground(Color.BLACK);
    }

    private void mettreAJour() {

    }

    /**
     * Cette méthode est la boucle principale du jeu qui contrôle la fréquence d'images et met à jour l'état du jeu.
     * Elle est conçue pour fonctionner à un taux spécifique d'images par seconde (FPS).
     */
    @Override
    public void run() {
        // Calcule l'intervalle en nanosecondes entre chaque image en fonction des FPS désirés
        double intervalle = (double) 1000000000 / FPS;
        // Variation de temps pour accumuler les différences de temps entre les images
        double variationTemps = 0;
        // Enregistre le dernier moment où la boucle a été exécutée
        long dernierTemps = System.nanoTime();
        long tempsActuel;

        // Continue la boucle de jeu tant que le thread du jeu est actif
        while(threadDeJeu != null) {
            // Obtient le temps actuel en nanosecondes
            tempsActuel = System.nanoTime();
            // Accumule la fraction de temps écoulé par rapport à l'intervalle de frame que j'ai précisé
            variationTemps += (tempsActuel - dernierTemps) / intervalle;
            // Met à jour le dernier temps au temps actuel
            dernierTemps = tempsActuel;

            // Si suffisamment de temps s'est écoulé pour une image, mettre à jour l'état du jeu et rendre
            if(variationTemps >= 1) {
                mettreAJour(); // Met à jour la logique du jeu
                repaint(); // Rend le jeu
                // Décrémente la variation de temps de 1 pour tenir compte de l'image qui vient d'être traitée
                variationTemps--;
            }
        }
    }


    public void commencerJeu() {
        threadDeJeu = new Thread(this);
        threadDeJeu.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        echiquier.dessiner(g2);
    }

}
