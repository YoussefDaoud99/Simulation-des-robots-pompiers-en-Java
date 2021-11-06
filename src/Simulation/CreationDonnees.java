


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;



/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class CreationDonnees{
	

    /**
     * Lit le contenu d'un fichier de donnees (cases,
     * robots et incendies) et fait appel aux methodes
     * de creation de la carte, les incendies et les robots
     * @param fichierDonnees nom du fichier à lire
     * @throws DataFormatException si le fichier n'est pas trouve
     * @return les donnees lu a partir de fichierDonnees
     */
    public static DonneesSimulation creer(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        CreationDonnees lecteur = new CreationDonnees(fichierDonnees);
        DonneesSimulation donnees = new DonneesSimulation(null, null, null);
        donnees.setCarte(lecteur.creerCarte());
        Carte ma_carte = donnees.getCarte();
        donnees.setListeIncendies(lecteur.creerIncendies());
        donnees.setListeRobot(lecteur.creerRobots(ma_carte));
        scanner.close();
        return donnees;
    }


    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private CreationDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et cree les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private Carte creerCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
            Case[][] listeCases = new Case[nbLignes][nbColonnes];
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                	listeCases[lig][col] = creerCase(lig, col);
                }
            }
            Carte carte = new Carte(nbLignes,nbColonnes,tailleCases, listeCases);
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis creeCase est remontee telle quelle
    }


    
    
    
    /**
     * Lit et cree les donnees d'une case.
     */
    private Case creerCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();
            return new Case(lig,col,nature);
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
    }


    /**
     * Lit et cree les donnees des incendies.
     */
    private Incendie[] creerIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            Incendie [] incendies = new Incendie[nbIncendies];
            for (int i = 0; i < nbIncendies; i++) {
                incendies[i] = creerIncendie(i);
            }
        return incendies;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et cree les donnees du i-eme incendie.
     * @param i
     */
    private Incendie creerIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();
            return new Incendie(new Case(lig, col), intensite); 

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et cree les donnees des robots.
     * @param ma_carte 
     */
    private Robot[] creerRobots(Carte ma_carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            Robot[] robots = new Robot[nbRobots];
            for (int i = 0; i < nbRobots; i++) {
                robots[i] = creerRobot(i, ma_carte);
            }
            return robots;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et cree les donnees du i-eme robot.
     * @param i
     * @param ma_carte 
     */
    private Robot creerRobot(int i, Carte ma_carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();
            // lecture eventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");
        	int vitesse = -1;
            if (s != null) {
            	vitesse = Integer.parseInt(s);
            }
            verifieLigneTerminee();
            if (type.equals("DRONE")) {
            	Robot drone = new Drone(new Case(lig, col), vitesse);
            	drone.setCarte(ma_carte);
            	return drone;
            }
            
            else if (type.equals("PATTES")) {
            	Robot pattes = new RobotAPattes(new Case(lig, col));
            	pattes.setCarte(ma_carte);
            	return pattes;
            }
            
            else if (type.equals("ROUES")) {
            	Robot roues = null;
            	if (vitesse == -1){
            		roues = new RobotARoues(new Case(lig, col));
            	}
            	else {
            		roues = new RobotARoues(new Case(lig, col), vitesse);
            	}
            	roues.setCarte(ma_carte);
            	return roues;
            }
            
            else if (type.equals("CHENILLES")) {
            	Robot chenilles = null;
            	if (vitesse == -1){
            		chenilles = new RobotChenilles(new Case(lig, col));
            	}
            	else {
            		chenilles = new RobotChenilles(new Case(lig, col), vitesse);
            	}
            	chenilles.setCarte(ma_carte);
            	return chenilles;
            }
            System.out.println();
            return null;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }


}
