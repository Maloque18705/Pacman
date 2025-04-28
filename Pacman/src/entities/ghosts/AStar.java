package entities.ghosts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class AStar {
    public static List<int[]> findPath(int[][] grid, int startRow, int startCol, int goalRow, int goalCol) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
        Set<String> closedList;
        closedList = new HashSet<>();
        Map<String, Node> allNodes = new HashMap<>();

        Node startNode = new Node(startRow, startCol, 0, heuristic(startRow, startCol, goalRow, goalCol));
        openList.add(startNode);
        allNodes.put(startRow + "," + startCol, startNode);

        int[] dRow = { -1, 1, 0, 0 }; // Up, down, left, right
        int[] dCol = { 0, 0, -1, 1 };

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            String currentKey = current.row + "," + current.col;

            if (current.row == goalRow && current.col == goalCol) {
                return reconstructPath(current);
            }

            closedList.add(currentKey);

            for (int i = 0; i < 4; i++) {
                int newRow = current.row + dRow[i];
                int newCol = current.col + dCol[i];
                String newKey = newRow + "," + newCol;

                if (isValid(grid, newRow, newCol) && !closedList.contains(newKey)) {
                    double gCost = current.gCost + 1;
                    double hCost = heuristic(newRow, newCol, goalRow, goalCol);
                    double fCost = gCost + hCost;

                    Node neighbor = allNodes.getOrDefault(newKey, new Node(newRow, newCol, gCost, hCost));
                    if (!allNodes.containsKey(newKey)) {
                        neighbor.parent = current;
                        openList.add(neighbor);
                        allNodes.put(newKey, neighbor);
                    } else if (gCost < neighbor.gCost) {
                        neighbor.gCost = gCost;
                        neighbor.fCost = fCost;
                        neighbor.parent = current;
                        openList.remove(neighbor);
                        openList.add(neighbor);
                    }
                }
            }
        }
        return null; // Không tìm thấy đường đi
    }

    private static boolean isValid(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == 0;
    }

    private static double heuristic(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2); // Manhattan distance
    }

    private static List<int[]> reconstructPath(Node node) {
        List<int[]> path = new ArrayList<>();
        while (node != null) {
            path.add(0, new int[] { node.row, node.col });
            node = node.parent;
        }
        return path;
    }

    static class Node {
        int row, col;
        double gCost, hCost, fCost;
        Node parent;

        Node(int row, int col, double gCost, double hCost) {
            this.row = row;
            this.col = col;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }
    }
}