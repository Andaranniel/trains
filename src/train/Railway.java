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
	/*
	 * Méthode qui retourne une position (même element mais direction opposé)
	 * 
	 * @param pos Position initiale
	 * 
	 * @return Une nouvelle position dont la direction est opposé
	 * 
	 * @author BLEVIN Sarah
	 * @author COURSODON Liam
	 * 
	 */
	public Position seRetourner(Position pos) {
		Direction dir = pos.getDir();
		if(dir.toString()=="from left to right") {
			return new Position(pos.getElt(), Direction.RL);
		}
		return new Position(pos.getElt(), Direction.LR);
	}
	
	/*
	 * Méthode qui calcule une nouvelle position pour un train qui avance d'un cran sur le railway 
	 * 
	 * @param pos Position initiale
	 * 
	 * @return Une nouvelle position un cran plus loin
	 * 
	 * @author BLEVIN Sarah
	 * @author COURSODON Liam
	 * 
	 * Amelioration possible : erreur si pos ne contient pas d'element du railway
	 */
	public Position nextStop(Position pos) {
		Element elt = pos.getElt();
		Direction dir = pos.getDir();
		
		
		for(int i=0; i < elements.length; i++) { //on parcourt les éléments du railway
			if(elements[i].equals(elt)) {
				
				if(dir.toString()=="from left to right") {
					if(i+1<elements.length) {
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
		return null;
		
	}
	
	/*
	 * Méthode qui détermine si il y a un risque d'interblocage lors du départ d'un train  
	 * 
	 * @param p Position initiale
	 * 
	 * On parcours tout les elements
	 * On ignore l'element actuel ainsi que les gares 
	 * Et parmis ceux qui nous interesse on regarde si un train ne bloque pas le passage 
	 * (ie : sur le chemin et dans le sens inverse)
	 * 
	 * @return Un boolean true si le risque existe et false sinon
	 * 
	 * @author BLEVIN Sarah
	 * @author COURSODON Liam
	 * 
	 * Amelioration possible : Verifier qu'il reste de la place en gare 
	 * et plus difficilement vérifier qu'il RESTERA de la place en gare si par exemple deja plusieur trains sont sur le railway
	 */
	public synchronized boolean stopInterblocage(Position p) throws InterruptedException{

		for (Element e : elements) { 
			if(!e.equals(p.getElt()) && !(e instanceof Station)) {
				if(p.getDir().toString().equals("from left to right")) {
					if(e.getCountRL() > 0) { 
						return true;
					}
				}
				if(p.getDir().toString().equals("from right to left")) {
					if(e.getCountLR()>0) {
						return true;
					}
					
				}
					
					
				}
			else {
				//
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
