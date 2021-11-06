/**
 * C'est une classe fille de la classe Evenement
 * Cette Classe permet de manipuler les evenements liés au robot
 * @author Equipe
 * @version 1
 */
public class EvenementRobot extends Evenement{
	private Robot robot;
	private String evenement;
	private Direction dir;
	
	/**
	 * Initialise un nouveau evenement lié au robot avec la date, le robot, l'venement et la direction
	 * @param date : La date de l'evenement
	 * @param robot : Le robot qui va s'occuper de l'evenement
	 * @param evenement : La description de l'evenemnt; soit deplacer, remplir, occuper ou liberer
	 * deplacer : Consiste a deplacer le robot a une case donnee
	 * remplir : Consiste a remplir le reservoir
	 * occuper/liberer : Consiste a changer le statut du robot s'il est occupé on non
	 * @param dir : La direction du deplacement du robot
	 */
	public EvenementRobot(double date, Robot robot, String evenement, Direction dir) {
		super(date);
		this.robot = robot;
		this.evenement = evenement;
		this.dir = dir;
	}
	
	/**
	 * Initialise un nouveau evenement lié au robot avec la date, le robot, l'venement et la direction
	 * @param date : La date de l'evenement
	 * @param robot : Le robot qui va s'occuper de l'evenement
	 * @param evenement : La description de l'evenemnt; soit deplacer, remplir, occuper ou liberer
	 * deplacer : Consiste a deplacer le robot a une case donnee
	 * remplir : Consiste a remplir le reservoir
	 * occuper/liberer : Consiste a changer le statut du robot s'il est occupé on non
	 */
	public EvenementRobot(double date, Robot robot, String evenement) {
		super(date);
		this.robot = robot;
		this.evenement = evenement;
	}

	/**
	 * Retourne Le robot
	 * @return le robot qui effectue l'evenement
	 */
	public Robot getRobot() {
		return robot;
	}

	/**
	 * execute l'evenement selon sa descrpition
	 */
	public void execute() {
		if (evenement.equals("deplacer")) {
			getRobot().deplace(dir);
		}
		if (evenement.equals("remplir")) {
			getRobot().remplirReservoir();
		}
		if (evenement.equals("occuper")) {
			getRobot().setOccupe(true);
		}
		if (evenement.equals("liberer")) {
			getRobot().setOccupe(false);
		}
	}
}
