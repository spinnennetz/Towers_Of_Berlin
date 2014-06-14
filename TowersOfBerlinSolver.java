import java.util.Stack;

/**
 * TowersOfBerlinSolver.java
 * @author Johanna Tengler
 */
public class TowersOfBerlinSolver {
	/** The left tower. **/
	Tower left;
	/** The middle tower. **/
	Tower middle;
	/** The right tower. **/
	Tower right;
	/** An array that stores the three towers. **/
	Tower[] towerarray;
	
	/** A stack that stores the actions needed for solving the problem. **/
	Stack<DiskAction> actions = new Stack<DiskAction>();
	/** A stack that stores the actions needed for solving the problem in reverse order. **/
	Stack<DiskAction> actions2 = new Stack<DiskAction>();	
	
	/**
	 * Constructs the left, middle and right tower and the array that stores those towers.
	 * @param left, the left tower
	 * @param middle, the middle tower
	 * @param right, the right tower
	 */
	public TowersOfBerlinSolver( Tower left, Tower middle, Tower right) {
		this.left=left;
		this.middle=middle;
		this.right=right;
		towerarray = new Tower[3];
		towerarray[0]= left;
		towerarray[1]= middle;
		towerarray[2]= right;
	}
	
	/**
	 * A recursive method that merges two tower with the height {@code index}.
	 * @param tower1, the first tower
	 * @param tower2, the first tower
	 * @param index, the index
	 * @param empty, the empty tower, used to merge the first and second tower
	 */
	public void merge(int tower1, int tower2, int index, int empty) {
		if (index==1) {
			//merge a tower with the height 1
			moveDisk(tower1, tower2);
		} else if (index==2) {
			//merge a tower with the height 2
			moveDisk(tower1, empty);
			moveDisk(tower2, empty);
			moveDisk(tower1, tower2);
			moveDisk(empty, tower2);
			moveDisk(empty, tower2);
		}
		if (index>2){
			merge(tower1, tower2, index-1, empty);
			moveTower(tower2, tower1, (index*2)-2, empty);
			moveDisk (tower1, tower2);
			moveTower(empty, tower1, (index*2)-2, tower2);
		}
	}
	
	/**
	 * A recursive method that moves a tower with the height {@code index} to another place.
	 * @param tower1, the tower that will be moved
	 * @param tower2, the place the tower is moved to
	 * @param index, the height
	 * @param empty, the empty tower, used to move the tower to the place
	 */
	public void moveTower(int tower1, int tower2, int index, int empty) {
		if(index>0) { 
			moveTower(tower1, empty, index-1, tower2);
			moveDisk(tower1, empty);
			moveTower(tower2, tower1, index-1, empty);
		}                                                                
	}

	/**
	 * A method that moves a disc from one tower to another.
	 * @param from, the tower the disc is moved from
	 * @param to, the tower the disc will be moved to
	 */
	private void moveDisk( int from, int to ) {
		towerarray[to].addDisk(towerarray[from].peek());
		towerarray[from].removeDisk();
		DiskAction action = new DiskAction(from, to);
		actions.add(action);
	}

	/**
	 * A method that reverses order of {@code actions}.
	 * @return actions2, a stack that stores the actions needed for solving the problem in reverse order
	 */
	public Stack<DiskAction> getActions() {
		while(!actions.empty()) {
			actions2.push(actions.peek());
			actions.pop();
		}
		return actions2;
	}
	
	/**
	 * A method that solves the problem.
	 */
	public void solve() {
		int disk=middle.countDisk();
		int diff=0;
		merge(0, 1, disk-1, 2);
		moveDisk(0, 2);
		moveTower(1, 2, middle.countDisk(), 0);
		moveDisk(2,1);
		while(middle.countDisk()!=disk) {
			diff=Math.abs(left.countDisk()-middle.countDisk()-1);
			moveTower(0, 2, diff, 1);
			moveTower(1, 2, diff-1, 0);
		}
	}

}
