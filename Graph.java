package Project;

public class Graph {
	private static final int INF = Integer.MAX_VALUE; // INF represents the infinite weight
	protected int[][] W; // the adjacent matrix
	protected int n;

	public Graph(int num_vertices) {
		n = num_vertices;
		W = new int[n][n];

		// Initialize the adjacency matrix
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (i == j)
					W[i][j] = 0;
				else
					W[i][j] = INF;
	}

	// Accessors
	// Returns the number of the vertices
	public int NumberOfVertices() {
		return n;
	}

	// Returns a copy of the adjacency matrix
	public int[][] getCopyOfMatrix() {
		int[][] answer = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				answer[i][j] = W[i][j];
		return answer;
	}

	// Returns the weight of edge (u, v)
	public int getWeightOf(int u, int v) {
		return W[u][v];
	}

	// Returns the sum of two weights
	// Don't directly add two weights or you could have an overflow (for example,
	// 1+INF)
	// Use this static method to add two weights, getWeightSum(weight1, weight2)
	public static int getWeightSum(int a, int b) {
		if (a == INF || b == INF)
			return INF;
		return a + b;
	}

	// Returns the weight of edge (u, v)
	public int compare(int a, int b) {
		if (a == INF) {
			if (b == INF)
				return 0;
			return 1;
		} else {
			if (b == INF)
				return -1;

			if (a > b)
				return 1;
			else if (a < b)
				return -1;
			return 0;
		}
	}

	// Returns the weight of edge (u, v)
	public static int INF() {
		return INF;
	}

	// Prints the adjacency matrix
	public void printAdjacentMatrix() {
		printMatrix("Adjacent Matrix", W);
	}

	// Prints the general matrix
	public void printMatrix(String name, int[][] a) {
		System.out.println(name + " = ");
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (a[i][j] == INF)
					System.out.print("  INF");
				else
					System.out.format("%5d", a[i][j]);
			}
			System.out.println();
		}
	}

	// Modifiers
	public void addEdge(int u, int v, int weight) {
		if (u < 0 || u >= n || v < 0 || v >= n)
			throw new IllegalArgumentException("Illegel Vertex Index!");
		if (u == v)
			return;
		W[u][v] = weight;
	}

	public void setAllZeroWeights() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				W[i][j] = 0;
	}

	// ===========================================================
	public static void main(String[] args) {

	}

	public void addUndirectedEdge(Integer integer, Integer integer2, int i) {
	}
}
