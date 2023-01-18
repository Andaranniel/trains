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
	
	public Position nextStop(Position pos) { //erreur elt pas ds le railway
		Element elt = pos.getElt();
		Direction dir = pos.getDir();
		for(int i=0; i < elements.length; i++) {
			if(elements[i].equals(elt)) {
				
				if(dir.toString()=="from left to right") {
					if(i+1<elements.length) { //peut-être une méthode qui détermine si on est ds une gare
						return new Position(elements[i+1],dir);
					}
					return seRetourner(pos);
				}
				
				else {
					if(i==0) {
						return seRetourner(pos);
					}
					return new Position(elements[i-1],dir);
				}
			}
		}
		System.out.println(elements[0]);
		System.out.println(elements[0].equals(elt));
		return null;
		
	}
	
	public synchronized void stopInterblocage(Position p) throws InterruptedException{
		/** Une méthode pour éviter les interblocages: au moment où un train quitte une gare,
		 * on vérifie qu'il n'y a pas déjà un train qui voyage en sens inverse sur le railway
		 * afin qu'ils ne se rencontrent pas au milieu.
		 * cette méthode prend en argument la position actuelle du train
		 */
		
		for (Element e : elements) { //on parcourt les éléments du railway
			if(!e.equals(p.getElt())) { //on ne s'intéresse pas à l'élément actuel de la position du train
				if(p.toString().equals("from left to right")) {
					if(e.getCountRL() !=0) {
						System.out.println("risque d'interblocage, le train ne part pas");
						wait();
					}
				}
				if(p.toString().equals("from right to left")) {
					if(e.getCountLR() !=0) {
						System.out.println("risque d'interblocage, le train ne part pas");
						wait();
					}
					
				}
					
					
				}
			}
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
