package train;


/**
 * Représentation d'un circuit constitué d'éléments de voie ferrée : gare ou
 * section de voie
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Railway {
	private final Element[] elements;

	public Railway(Element[] elements) {
		if(elements == null)
			throw new NullPointerException();
		
		this.elements = elements;
		for (Element e : elements)
			e.setRailway(this);
	}
	
	public Position seRetourner(Position pos) {
		Direction dir = pos.getDir();
		if(dir.toString()=="from left to right") {
			return new Position(pos.getElt(), Direction.RL);
		}
		return new Position(pos.getElt(), Direction.LR);
	}
	
	public Position nextStop(Position pos) { //amélioration: erreur elt pas ds le railway
		/** Une méthode pour déterminer la prochaine étape sur le railway à partir de la position actuelle d'un train **/
		Element elt = pos.getElt();
		Direction dir = pos.getDir();
		
		
		for(int i=0; i < elements.length; i++) { //on parcourt les éléments du railway
			if(elements[i].equals(elt)) {
				
				if(dir.toString()=="from left to right") {
					if(i+1<elements.length) { //amélioration: peut-être une méthode qui détermine si on est ds une gare
						//si on peut encore avancer dans la même direction, on le fait
						return new Position(elements[i+1],dir);
					}
					return seRetourner(pos); //sinon, on change de direction
				}
				
				else {
					if(i==0) {
						return seRetourner(pos); //de même, mais dans le cas d'une direction RL
					}
					return new Position(elements[i-1],dir);
				}
			}
		}
		System.out.println(elements[0]);
		System.out.println(elements[0].equals(elt));
		return null;
		
	}
	
	public synchronized boolean stopInterblocage(Position p) throws InterruptedException{
		/** Une méthode pour éviter les interblocages: au moment où un train quitte une gare,
		 * on vérifie qu'il n'y a pas déjà un train qui voyage en sens inverse sur le railway
		 * afin qu'ils ne se rencontrent pas au milieu.
		 * cette méthode prend en argument la position actuelle du train
		 */
		for (Element e : elements) { //on parcourt les éléments du railway
			if(!e.equals(p.getElt()) && !(e instanceof Station)) { //on ne s'intéresse pas à l'élément actuel de la position du train
				if(p.getDir().toString().equals("from left to right")) {
					//System.out.println("Element : " + p + "a " + e.getCountRL() + "train allant de r a l" );
					if(e.getCountRL() > 0) { //on ne veut pas de train déjà en train de voyager en sens inverse
						//System.out.println("risque d'interblocage, le train ne part pas");
						return true;
					}
				}
				if(p.getDir().toString().equals("from right to left")) {
					//System.out.println("Element : " + p + "a " + e.getCountLR() + "train allant de l a r" );
					if(e.getCountLR()>0) {
						//System.out.println("risque d'interblocage, le train ne part pas");
						return true;
					}
					
				}
					
					
				}
			else {
				//System.out.println("pas de risque d'interblocage, le train peut quitter la gare");
			}
			}
		return false;
	}

	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Element e : this.elements) {
			if (first)
				first = false;
			else
				result.append("--");
			result.append(e);
		}
		return result.toString();
	}
}
