/**
 * Cette classe permet de gerer les evenements
 * C est une classe mere de la classe EvenementIncendie et la classe EvenementRobot
 * @author Equipe
 * @version 1
 */
abstract public class Evenement {
	
	protected double date;
	
	/**
	 * initialise un nouveau evenement avec la valeur de la date
	 * @param date : la date de l'evenement
	 */
	public Evenement(double date){
		this.date = date;
	}
	
	/**
	 * Retourne la date
	 * @return la date de l evenement
	 */
	public double getDate() {
		return date;
	}
	/**
	 * Cette methode consiste a executer l evenement
	 * abstract methode car ça depend de chaque evenement
	 */
	public abstract void execute();
	
}
