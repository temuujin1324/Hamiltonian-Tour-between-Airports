package Project;

import static Project.adjacency.CreateDict;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.rmi.ConnectIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Line;
import javax.sound.sampled.ReverbType;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.crypto.Data;

public class HCP_skeleton {
	public static String remainingCount = "";
	static String[] names;

	public HashMap<Integer, Integer> reIndexedPairs = new HashMap<>();
	public HashMap<Integer, Integer> ReversereIndexedPairs = new HashMap<>();
	public static HashMap<Integer, String> LineResults = new HashMap<>();

	static JFrame frame = new JFrame();
	static JPanel panel = new JPanel();
	static JButton button = new JButton();
	static JTextField textField = new JTextField();
	static String usertext = "";
	static String[] SplicedInput;
	static JButton next = new JButton();
	static JButton prev = new JButton();

	public static int count = 0;
	public static int innercount = 0;
	private int[] vindex;
	private int num_solutions;
	private int n;
	private Graph g;

	public static String Result = "";

	public HCP_skeleton(int num_vertices) {
		g = new Graph(num_vertices);
		g.setAllZeroWeights();
		n = g.NumberOfVertices();
		vindex = new int[n];
	}

	public void solve(ArrayList<Integer> data) {
		vindex[0] = 0; // Start from vertex 1
		num_solutions = 0;
		int[] updateddata = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			updateddata[i] = data.get(i);
		}
		reIndex(updateddata);
		ReversereIndex(updateddata);

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

	public void CreateGraph(ArrayList<Integer> data) {
		for (Map.Entry<Integer, ArrayList<Integer>> entry : adjacency.Pairs.entrySet()) {
			// 20 ,30
			// [150, 20, 30 60]

			Integer key = entry.getKey();
			ArrayList<Integer> value = entry.getValue();
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < adjacency.Pairs.get(key).size(); j++) {
					for (int a = 0; a < data.size(); a++) {
						if (key == data.get(i)) {
							if (value.get(j) == null) {
								continue;
							}
							if (value.get(j) == data.get(a)) {

								/*
								 * System.out.println(adjacency.ReverseDict.get(data[i]) + " to "
								 * + adjacency.ReverseDict.get(data[a]));
								 */

								addUndirectedEdge(reIndexedPairs.get(data.get(a)) + 1,
										reIndexedPairs.get(data.get(i)) + 1,
										1);
							}
						}
					}
				}
			}
		}

	}

	public void hamiltonian(int i) {
		Result = "";

		if (promising(i)) {
			if (i == n - 1) {
				for (int a = 0; a < n; a++) {
					Result += adjacency.ReverseDict.get(ReversereIndexedPairs.get(vindex[a])) + " ";
					System.out.print(adjacency.ReverseDict.get(ReversereIndexedPairs.get(vindex[a])) + " ");

				}
				LineResults.put(count, Result);
				System.out.println();
				count++;

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

	public static void CreateFrame() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setTitle("TEAM 7!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(1280, 780);

		frame.getContentPane().setBackground(new Color(123, 50, 250));
		frame.add(panel);

		panel.setLayout(null);
		panel.setBackground(new Color(123, 50, 250));

		textField = new JTextField("Please enter your destinations(More than 2) seperated by commas!", 100);
		textField.setBounds(350, 200, 500, 30);

		panel.add(textField);

		button = new JButton("Submit");
		button.setBounds(350, 250, 100, 40);
		button.addActionListener((e) -> {
			submitAction();
		});

		panel.add(button);

		frame.setVisible(true);

	}

	public static void displayResult() {

		frame = new JFrame();
		panel = new JPanel();

		frame.setTitle("YOUR TOUR IS!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(1280, 780);
		frame.getContentPane().setBackground(new Color(123, 50, 250));

		next = new JButton("Next path");
		next.setBounds(750, 400, 100, 40);
		next.addActionListener((e) -> {
			submitNext();
		});
		prev = new JButton("Previous path");
		prev.setBounds(475, 400, 100, 40);

		prev.addActionListener((e) -> {
			submitPrev();
		});

		panel.add(next);
		frame.add(next);
		frame.add(prev);
		frame.add(panel);

		JLabel Text = new JLabel();
		Text.setText("YOUR TOUR IS........................");
		Text.setForeground(new Color(0, 0, 0));
		Text.setFont(new Font("MV BOLI", Font.PLAIN, 20));

		JLabel remaining = new JLabel();

		remainingCount = String.valueOf(LineResults.size() - innercount - 1);
		remaining.setText("Remaining possible tours :" + remainingCount);
		remaining.setHorizontalAlignment(JLabel.CENTER);

		remaining.setForeground(new Color(0, 0, 0));
		remaining.setFont(new Font("MV BOLI", Font.PLAIN, 20));

		panel.setLayout(null);
		panel.setBackground(new Color(123, 50, 250));
		JLabel label = new JLabel();

		Result = LineResults.get(innercount);
		if (innercount == LineResults.size()) {

			Result = "All of the solutions have been displayed";
			remaining.setText("Press Previous path to go back ");
			frame.remove(next);

		}

		if (innercount == 0) {
			frame.remove(prev);
		}

		if (innercount == -1) {
			frame.dispose();

		}
		label.setText(Result);

		label.setBounds(525, 50, 1000, 500);
		label.setForeground(new Color(230, 211, 163));
		label.setFont(new Font("MV BOLI", Font.PLAIN, 30));
		frame.add(label);
		frame.add(remaining);

		frame.setVisible(true);

	}

	public static void submitAction() {
		// You can do some validation here before assign the text to the variable
		usertext = textField.getText();
		receive(usertext);

	}

	public static void submitNext() {
		innercount++;
		displayResult();

	}

	public static void submitPrev() {
		innercount--;
		displayResult();

	}

	public static void receive(String input) {

		SplicedInput = usertext.split(",");
		ArrayList<Integer> data = new ArrayList<>();

		for (int i = 0; i < SplicedInput.length; i++) {
			data.add(adjacency.Dict.get(SplicedInput[i]));

		}
		HCP_skeleton skeleton = new HCP_skeleton(data.size());
		skeleton.solve(data);
		displayResult();
	}

	// ===========================================================
	public static void main(String[] args) throws IOException {

		adjacency.CreateDict();
		adjacency.CreatePairs();
		adjacency.CreateReverseDict();

		CreateFrame();
	}

}