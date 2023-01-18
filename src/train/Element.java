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
	
	public synchronized void enter(Direction d) throws InterruptedException {
		/** une méthode qui détermine si un train peut entrer dans l'élément **/
		
		while(!((this.countTrainLR+this.countTrainRL) <this.getSize())) {
			System.out.println("pas la place d'avancer");
			wait() ;}
			if(d.toString().equals("from left to right")) {
				this.countTrainLR++;
			}
			if(d.toString().equals("from right to left")) {
				this.countTrainRL++;
			}
	}
	
	public synchronized void leave(Direction d) {
		/** une méthode pour mettre à jour le nombre de trains lorsqu'un train quitte l'élément **/
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
