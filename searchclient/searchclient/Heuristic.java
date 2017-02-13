package searchclient;

import java.util.Comparator;

import searchclient.NotImplementedException;

public abstract class Heuristic implements Comparator<Node> {
	private static char[][] goalsCopy; 
	public Heuristic(Node initialState) {
		// Here's a chance to pre-process the static parts of the level.
		goalsCopy = initialState.goals;
	}

	public int h(Node n) {
		int estimate = n.MAX_ROW*n.MAX_COL;//Worst case where "all" cells are goals
		
		int dist = n.MAX_ROW*n.MAX_COL;
		
		for (int row = 1; row < n.MAX_ROW - 1; row++) {
			for (int col = 1; col < n.MAX_COL - 1; col++) {
				char b = Character.toLowerCase(n.boxes[row][col]);
				if (b > 0){
					int d = Math.abs(row-n.agentRow) + Math.abs(col-n.agentCol);
					if (dist > d){
						dist = d; 
					}
				}	
			}
		}
		

		for (int row = 1; row < n.MAX_ROW - 1; row++) {
			for (int col = 1; col < n.MAX_COL - 1; col++) {
				char g = goalsCopy[row][col];
				char b = Character.toLowerCase(n.boxes[row][col]);
				if (g > 0 && b != g) {
					estimate += 1;  // If the cell is a goalcell but there is no box on it, then add 1 to the estimate
				}
			}
		}
		return estimate + dist;
	}

	public abstract int f(Node n);

	@Override
	public int compare(Node n1, Node n2) {
		return this.f(n1) - this.f(n2);
	}

	public static class AStar extends Heuristic {
		public AStar(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return n.g() + this.h(n);
		}

		@Override
		public String toString() {
			return "A* evaluation";
		}
	}

	public static class WeightedAStar extends Heuristic {
		private int W;

		public WeightedAStar(Node initialState, int W) {
			super(initialState);
			this.W = W;
		}

		@Override
		public int f(Node n) {
			return n.g() + this.W * this.h(n);
		}

		@Override
		public String toString() {
			return String.format("WA*(%d) evaluation", this.W);
		}
	}

	public static class Greedy extends Heuristic {
		public Greedy(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return this.h(n);
		}

		@Override
		public String toString() {
			return "Greedy evaluation";
		}
	}
}
