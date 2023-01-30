package train;

/**
 * Représentation d'un train. Un train est caractérisé par deux valeurs :
 * <ol>
 *   <li>
 *     Son nom pour l'affichage.
 *   </li>
 *   <li>
 *     La position qu'il occupe dans le circuit (un élément avec une direction) : classe {@link Position}.
 *   </li>
 * </ol>
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Mayte segarra <mt.segarra@imt-atlantique.fr>
 * Test if the first element of a train is a station
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * @version 0.3
 */
public class Train implements Runnable{
	private final String name;
	private Position pos;

	public Train(String name, Position p) throws BadPositionForTrainException {
		if (name == null || p == null)
			throw new NullPointerException();

		// A train should be first be in a station
		if (!(p.getElt() instanceof Station))
			throw new BadPositionForTrainException(name);

		this.name = name;
		this.pos = p.clone();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Train[");
		result.append(this.name);
		result.append("]");
		result.append(" is on ");
		result.append(this.pos);
		return result.toString();
	}
	
	/** 
	 * Une méthode permettant à un train d'avancer d'un cran sur un railway
	 *  
	 *  @author BLEVIN Sarah
	 *  @author COURSODON Liam
	 *  
	 */
	public void move() throws InterruptedException {
		
		Element eltActuel = this.pos.getElt();
		Railway r = eltActuel.getRailway(); 

		Position newPos = r.nextStop(this.pos); //on détermine la prochaine étape sur le railway
		
		
		newPos.getElt().enter(this.pos,r); //on demande à entrer dans la prochaine étape
		
		//si ce n'est pas possible, la méthode enter met le thread en wait(). 
		//si ça l'est, la méthode enter fait les modifications nécessaires au niveau des section et on passe à la suite
		
		eltActuel.leave(this.pos.getDir()); //on quitte formellement l'étape précédente
		
		this.pos= newPos; //on met à jour la position
		
		System.out.println("le train" + this.name + " est maintenant en position:"+this.pos);
		
	}

	@Override
	public void run() {
		do {
			try {
				move(); //le train essaye d'avancer tant que cela lui est possible
				Thread.sleep(1000); //On ajoute un delais pour rendre l'affichage console plus lisible
				System.out.println(""); //Permet de bien distinguer quand les actions sont effectué pour rendre l'affichage plus lisible
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while(true);
		
	}
}
