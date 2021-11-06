

import java.awt.Color;
import java.io.FileNotFoundException;

import java.util.zip.DataFormatException;

import gui.GUISimulator;

/**
 * class de Test de notre Simulation
 * @author Equipe
 * @version 1
 */

public class TestGraphique {
	
	/**
	 * Le main
	 * @param args Les arguments d'entree
	 * @throws FileNotFoundException si le fichier n'est pas trouvee
	 * @throws DataFormatException Signals that a data format error has occurred
	 */
    public static void main(String[] args) throws FileNotFoundException, DataFormatException {
    	String fichier = "carteSujetTest.map";
        DonneesSimulation mesDonnees = CreationDonnees.creer(fichier);
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);
        Simulateur simulateur = new Simulateur(0, gui, mesDonnees);
        // Choississez une stratégie
        Strategie strategie = new StrategieEvoluee(mesDonnees, simulateur);               //  pour une stratégie évoluée
        //Strategie strategie = new StrategieElementaire(mesDonnees, simulateur);             //pour une stratégie élementaire
    }

    /**
     * Le test des deux senarios de la partie 2
     * @param robot1 Le robot utilise dans le test
     * @param incendie1 l'incendie utilisee dans le test
     * @param simulateur le simulateur
     */
    public static void test1(Robot robot1, Incendie incendie1, Simulateur simulateur) {
        Evenement event1 = new EvenementRobot(2, robot1, "deplacer", Direction.NORD);
        Evenement event2 = new EvenementIncendie(2, incendie1, robot1);
        Evenement event3 = new EvenementRobot(3, robot1, "deplacer", Direction.OUEST);
        Evenement event4 = new EvenementRobot(4, robot1, "remplir");
        Evenement event5 = new EvenementRobot(5, robot1, "deplacer", Direction.EST);
        //Evenement event6 = new EvenementIncendie(6, incendie1, robot1);
          //scenario 1:
        simulateur.ajouteEvenement(event1);
        simulateur.ajouteEvenement(event1);
        simulateur.ajouteEvenement(event1);
        simulateur.ajouteEvenement(event1);
        
        
        // scenario 2:
        simulateur.ajouteEvenement(event1);
        simulateur.ajouteEvenement(event2);
        simulateur.ajouteEvenement(event3);
        simulateur.ajouteEvenement(event3);
        simulateur.ajouteEvenement(event4);
        simulateur.ajouteEvenement(event5);
        simulateur.ajouteEvenement(event5);
        //simulateur.ajouteEvenement(event6);
    }

}