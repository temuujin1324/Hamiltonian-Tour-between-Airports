package Project;

import static Project.adjacency.CreateDict;

import java.io.IOException;
import java.rmi.ConnectIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.Data;

public class HCP_skeleton {
	public HashMap<Integer, Integer> reIndexedPairs = new HashMap<>();
	public HashMap<Integer, Integer> ReversereIndexedPairs = new HashMap<>();

	private int[] vindex;
	private int num_solutions;
	private int n;
	private Graph g;

	public HCP_skeleton(int num_vertices) {
		g = new Graph(num_vertices);
		g.setAllZeroWeights();
		n = g.NumberOfVertices();
		vindex = new int[n];
	}

	public void solve(int[] data) {
		vindex[0] = 0; // Start from vertex 1
		num_solutions = 0;
		reIndex(data);
		ReversereIndex(data);

		CreateGraph(data);

		hamiltonian(0);
		if (num_solutions == 0) {
			System.out.println("No solution!");
		}
	}

	public void addUndirectedEdge(int u, int v, int w) {
		g.addEdge(u - 1, v - 1, w);
		g.addEdge(v - 1, u - 1, w);
	}

	public HashMap reIndex(int[] data) {
		for (int i = 0; i < data.length; i++) {
			reIndexedPairs.put(data[i], i);

		}
		return reIndexedPairs;

	}

	public HashMap ReversereIndex(int[] data) {
		for (Map.Entry<Integer, Integer> entry : reIndexedPairs.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			ReversereIndexedPairs.put(value, key);

		}
		return ReversereIndexedPairs;

	}

	public void CreateGraph(int[] data) {
		for (Map.Entry<Integer, ArrayList<Integer>> entry : adjacency.Pairs.entrySet()) {
			// 20 ,30
			// [150, 20, 30 60]

			Integer key = entry.getKey();
			ArrayList<Integer> value = entry.getValue();
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < adjacency.Pairs.get(key).size(); j++) {
					for (int a = 0; a < data.length; a++) {
						if (key == data[i]) {
							if (value.get(j) == null) {
								continue;
							}
							if (value.get(j) == data[a]) {

								/*
								 * System.out.println(adjacency.ReverseDict.get(data[i]) + " to "
								 * + adjacency.ReverseDict.get(data[a]));
								 */

								addUndirectedEdge(reIndexedPairs.get(data[a]) + 1, reIndexedPairs.get(data[i]) + 1,
										1);
							}
						}
					}
				}
			}
		}

	}

	public void hamiltonian(int i) {

		if (promising(i)) {
			if (i == n - 1) {
				for (int a = 0; a < n; a++) {

					System.out.print(adjacency.ReverseDict.get(ReversereIndexedPairs.get(vindex[a])) + " ");
				}
				System.out.println();

				num_solutions += 1;
			} else {
				for (int j = 1; j < n; j++) {
					vindex[i + 1] = j;

					hamiltonian(i + 1);
				}
			}
		}
	}

	public boolean promising(int i) {
		int j;
		boolean swich;
		if (i == n - 1 && 1 != g.getWeightOf(vindex[n - 1], vindex[0])) {
			return false;
		}

		else if (i > 0 && 1 != g.getWeightOf(vindex[i - 1], vindex[i])) {
			return false;
		}

		else {
			swich = true;
			j = 0;
			while (j < i && swich == true) {
				if (vindex[i] == vindex[j]) {
					return false;
				}
				j++;
			}
		}

		return true;
	}

	// ===========================================================
	public static void main(String[] args) throws IOException {
		adjacency.CreateDict();
		adjacency.CreatePairs();
		adjacency.CreateReverseDict();
		int[] data = { adjacency.Dict.get("LAX"),
				adjacency.Dict.get("JFK"),
				adjacency.Dict.get("ORD"),
				adjacency.Dict.get("MAD"), adjacency.Dict.get("CDG"), adjacency.Dict.get("FCO"),
				adjacency.Dict.get("ACC"),
				adjacency.Dict.get("LOS")
		};
		HCP_skeleton skeleton = new HCP_skeleton(data.length);

		skeleton.solve(data);
	}

}