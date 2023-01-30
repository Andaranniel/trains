package train;

/**
 * Cette classe abstraite est la représentation générique d'un élément de base d'un
 * circuit, elle factorise les fonctionnalitÃ©s communes des deux sous-classes :
 * l'entrée d'un train, sa sortie et l'appartenance au circuit.<br/>
 * Les deux sous-classes sont :
 * <ol>
 *   <li>La représentation d'une gare : classe {@link Station}</li>
 *   <li>La représentation d'une section de voie ferrée : classe {@link Section}</li>
 * </ol>
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public abstract class Element {
	private final String name;
	protected Railway railway;
	public int countTrainRL; //le nombre de trains dans l'élément actuellement
	public int countTrainLR; 
	public int size; //le nombre max de trains que peut contenir l'élement

	protected Element(String name) {
		if(name == null)
			throw new NullPointerException();
		
		this.name = name;
	}
	
	public int getCountLR() {
		/** une méthode pour obtenir le nombre de trains LR actuellement dans l'élément **/
		return this.countTrainLR;
	}
	
	public int getCountRL() {
		/** une méthode pour obtenir le nombre de trains RL actuellement dans l'élément **/
		return this.countTrainRL;
	}
	
	public void setCountRL(int i) {
		/** une méthode pour modifier le nombre de trains RL actuellement dans l'élément **/
		this.countTrainRL = i;
	}
	
	public void setCountLR(int i) {
		/** une méthode pour modifier le nombre de trains LR actuellement dans l'élément **/
		this.countTrainLR = i;
	}
	
	
	public boolean equals(Object o) {
		if(o instanceof Element) {

			if(o.toString()==this.toString()) {
				return true;
				}
			return false;
		}
		return false;
	}
		
	
	public void setRailway(Railway r) {
		if(r == null)
			throw new NullPointerException();
		
		this.railway = r;
	}
	
	public Railway getRailway() {
		return railway;
		
	}
	
	/*
	 * Méthode synchronize qui determine si un train peut rentrer dans une position
	 * 
	 * @param pos : Position initiale du train 
	 * @param r : Railway sur lequel le train se déplace
	 * 
	 * La méthode vérifie que le train puisse avancer
	 * ie pas de risque d'interblocage et de la place dans l'element sur lequel il souhaite entrer
	 * Si ce n'est pas le cas il attend
	 * 
	 * @author BLEVIN Sarah
	 * @author COURSODON Liam
	 *
	 */
	public synchronized void enter(Position pos, Railway r) throws InterruptedException {
		Direction d = pos.getDir();
		while(!((this.countTrainLR+this.countTrainRL) <this.getSize()) || r.stopInterblocage(pos)) {
			wait() ;}
			if(d.toString().equals("from left to right")) {
				this.countTrainLR++;
			}
			if(d.toString().equals("from right to left")) {
				this.countTrainRL++;
			}
	}
	
	/*
	 * Méthode synchronize qui fait quitter une position a un train
	 * 
	 * @param d : Direction du train 
	 * 
	 * La méthode indique a l'élément (this) que le train est partie
	 * 
	 * @author BLEVIN Sarah
	 * @author COURSODON Liam
	 *
	 */
	public synchronized void leave(Direction d) {
		if(d.toString().equals("from left to right")) {
			this.countTrainLR = this.countTrainLR -1;
		}
		if(d.toString().equals("from right to left")) {
			this.countTrainRL = this.countTrainRL -1;
		}
		notifyAll();
	}

	@Override
	public String toString() {
		return this.name;
	}

	protected abstract int getSize();
	/** méthode abstraite pour obtenir le nombre de trains max que peut contenir l'élément. Cela
	 * dépend du type d'élément
	 **/
	
}
