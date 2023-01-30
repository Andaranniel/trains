package train;

/**
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {
	// Un petit test sur un system simple
	public static void main(String[] args) {
		Station A = new Station("GareA", 3);
		Station D = new Station("GareD", 3);
		Section AB = new Section("AB");
		Section BC = new Section("BC");
		Section CD = new Section("CD");
		Railway r = new Railway(new Element[] { A, AB, BC, CD, D });
		System.out.println("The railway is:");
		System.out.println("\t" + r);
		Position p = new Position(A, Direction.LR);
		Position p2 = new Position(D, Direction.RL);
		try {
			Train t1 = new Train("1", p);
			Train t2 = new Train("2", p2);
			Train t3 = new Train("3", p);
			System.out.println(t1);
			System.out.println(t2);
			System.out.println(t3);
			System.out.println(D.getSize());
			
			//Création des threads
			Thread th1 = new Thread(t1);
			Thread th2 = new Thread(t2);
			Thread th3 = new Thread(t3);
			
			//Lancement des threads
			System.out.println("\n\n\n lancement train 1 \n\n\n");
			th1.start();
			System.out.println("\n\n\n lancement train 2 \n\n\n");
			th2.start();
			System.out.println("\n\n\n lancement train 3 \n\n\n");
			th3.start();
		} catch (BadPositionForTrainException e) {
			System.out.println("Le train " + e.getMessage());
		}

	}
}
