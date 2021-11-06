import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Classe representant un sommet 
 * @author Equipe
 * @version 1
 */
public class Sommet {
    private Case maCase;

    private List<Sommet> pccs = new ArrayList<>();

    private Double distance = Double.MAX_VALUE;

    Map<Sommet, Double> sommetsAdjacents = new HashMap<>();

    /**
     * Ajoute le sommet de destination avec sa distance du sommet actuelle au dictionnaire SommetsAdjacents
     * @param destination : Le sommet de destination (Sommet Adjacent)
     * @param distance : la distance de ce sommet
     */
    public void ajouterDestination(Sommet destination, double distance) {
        sommetsAdjacents.put(destination, distance);
    }
    
    /**
     * Ajoute le sommet de destination avec sa distance du sommet actuelle au dictionnaire SommetsAdjacents
     * La distance dans ce cas ets egale a 0
     * @param destination Le sommet de destination (Sommet Adjacent)
     */
    public void ajouterDestination(Sommet destination) {
        sommetsAdjacents.put(destination, 0.);
    }
    
    /**
     * Initialise le sommet par la case
     * @param macase : ma case
     */
    public Sommet(Case macase) {
        setMaCase(macase);
    }
    
    /**
     * Retourne le plus court chemin depuis la source
     * @return le plus court chemin depuis la source
     */
	public List<Sommet> getPccs() {
		return pccs;
	}
	
	/**
	 * Set le plus cours chemin
	 * @param pccs : le plus cours chemin
	 */
	public void setPccs(List<Sommet> pccs) {
		this.pccs = pccs;
	}
	
	/**
	 * Retourne la distance du sommet de la source
	 * @return la distance du sommet de la source
	 */
	public Double getDistance() {
		return distance;
	}
	
	/**
	 * Set la distance entre ce sommet et la source
	 * @param distance : la distance entre ce sommet et la source
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	/**
	 * Retourne la case qu'on va representer par le sommet
	 * @return la case qu'on va representer par le sommet
	 */
	public Case getMaCase() {
		return maCase;
	}
	
	/**
	 * Set la case qu'on va representer par le sommet
	 * @param thisCase : la case qu'on va representer par le sommet
	 */
	public void setMaCase(Case thisCase) {
		this.maCase = thisCase;
	}
	
	/**
	 * Retourne le dictionnaire comportant le sommet et sa distance avec la source
	 * @return le dictionnaire comportant le sommet et sa distance avec la source
	 */
	public Map<Sommet, Double> getSommetsAdjacents(){
		return this.sommetsAdjacents;
	}
	
	public void setSommetsAdjacents(Map<Sommet, Double> sommetsAdj){
		this.sommetsAdjacents = sommetsAdj;
	}
	
	/**
	 * Set le sommet et son cout dans le dictionnaire des sommets adjacents
	 * @param destination : le sommet de destination
	 * @param cout : le cout de ce sommet
	 */
	public void setCoutDestination(Sommet destination, double cout) {
		getSommetsAdjacents().put(destination, cout);
	}
	
	/**
	 * Retourne un boolean si le sommet donne est le meme que notre sommet
	 * @param sommet le sommet dont on cherche la case correspondante
	 * @return true si le sommet donne est le meme que notre sommet, false sinon
	 */
	public boolean equals(Sommet sommet) {
		return getMaCase() == sommet.getMaCase();
	}
}
