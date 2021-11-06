/**
 * C est une classe fille de la classe Evenement
 * Cette Classe permet de manipuler les evenements liés a l'incendie
 * @author Equipe
 * @version 1
 */

public class EvenementIncendie extends Evenement{
	private Incendie incendie;
	private Robot robot;
	private String eventType;
	
	/**
	 * Initialise un evenement lié a l'incendie avec la date de l evenement, l'incendie et le robot 
	 * @param date : La date de l'evenement
	 * @param incendie : L'incendie a eteindre
	 * @param robot : Le robot qui va eteindre l'incendie
	 */
	public EvenementIncendie(double date, Incendie incendie, Robot robot) {
		super(date);
		this.incendie = incendie;
		this.robot = robot;
		setEventType("deverser");
		}
	
	public EvenementIncendie(double date, Incendie incendie, Robot robot, String type) {
		super(date);
		this.incendie = incendie;
		this.robot = robot;
		setEventType(type);
		}
	
	/**
	 * Cette methode consiste a manipuler l'incendie
	 * Si Le nombre de litres necessaire a eteindre l'incendie est inferieur au nombre de litres au reservoir du robot
	 * Alors l'incendie n'est pas encore eteint et le robot doit remplir son reservoir
	 * Sinon l'incendie est disparu
	 */
	public void execute() {
		if (getEventType().equals("deverser")) {
			int interventionUnitaire = (int) this.robot.interventionUnitaire()[0];
			int nbLitresRestants = this.incendie.getNbLitres() - interventionUnitaire;
			this.incendie.setNbLitres(nbLitresRestants);
			this.robot.setReservoirAvance(this.robot.getReservoir()-interventionUnitaire);
			if (incendie.getNbLitres() <= 0 && robot.getNature().equals("PATTES") && robot.getStrategie()!=null && robot.getStrategie().getClass()==StrategieEvoluee.class) {
				robot.getStrategie().interventionPlusProcheIncendieNonAffecte(robot);
			}
		}
		else if (getEventType().equals("affecter")) {
			incendie.incrementeAffectations();
		}
		else if (getEventType().equals("desaffecter")) {
			incendie.decrementeAffectations();
		}
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
}
