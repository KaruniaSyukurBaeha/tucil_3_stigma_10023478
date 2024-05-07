import java.util.*;

public class WordLadder {
    private List<String> dictionary;

    // Constructor untuk memuat kata-kata dari file
    public WordLadder(List<String> dictionary) {
        this.dictionary = dictionary;
    }

    public List<String> findWordLadder(String startWord, String endWord, String algorithm) {
        if (!dictionary.contains(startWord) || !dictionary.contains(endWord)) {
            System.out.println("Start word or end word not found in dictionary.");
            return null;
        }

        if (algorithm.equals("UCS")) {
            return ucsAlgorithm(startWord, endWord);
        } else if (algorithm.equals("Greedy")) {
            return greedyAlgorithm(startWord, endWord);
        } else if (algorithm.equals("AStar")) {
            return aStarAlgorithm(startWord, endWord);
        } else {
            System.out.println("Invalid algorithm choice.");
            return null;
        }
    }

    private List<String> ucsAlgorithm(String startWord, String endWord) {
        Map<String, Integer> costMap = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new PriorityQueue<>(Comparator.comparingInt(costMap::get));
        Map<String, Integer> nodeLength = new HashMap<>(); // Map untuk menyimpan panjang node

        queue.add(startWord);
        costMap.put(startWord, 0);
        nodeLength.put(startWord, 1); // Panjang node awal adalah 1

        while (!queue.isEmpty()) {
            String currentWord = queue.poll();
            if (currentWord.equals(endWord)) {
                return reconstructPath(parentMap, endWord);
            }

            List<String> neighbors = getNeighbors(currentWord);
            for (String neighbor : neighbors) {
                int newCost = costMap.get(currentWord) + 1;
                if (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor)) {
                    costMap.put(neighbor, newCost);
                    parentMap.put(neighbor, currentWord);
                    queue.add(neighbor);
                    nodeLength.put(neighbor, nodeLength.get(currentWord) + 1); // Panjang node diupdate
                }
            }
        }

        return null; // No path found
    }

    private List<String> greedyAlgorithm(String startWord, String endWord) {
        Map<String, Integer> heuristicMap = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new PriorityQueue<>(Comparator.comparingInt(heuristicMap::get));
        Map<String, Integer> nodeLength = new HashMap<>(); // Map untuk menyimpan panjang node

        queue.add(startWord);
        heuristicMap.put(startWord, calculateHeuristic(startWord, endWord));
        nodeLength.put(startWord, 1); // Panjang node awal adalah 1

        while (!queue.isEmpty()) {
            String currentWord = queue.poll();
            if (currentWord.equals(endWord)) {
                return reconstructPath(parentMap, endWord);
            }

            List<String> neighbors = getNeighbors(currentWord);
            for (String neighbor : neighbors) {
                if (!heuristicMap.containsKey(neighbor)) {
                    heuristicMap.put(neighbor, calculateHeuristic(neighbor, endWord));
                    parentMap.put(neighbor, currentWord);
                    queue.add(neighbor);
                    nodeLength.put(neighbor, nodeLength.get(currentWord) + 1); // Panjang node diupdate
                }
            }
        }

        return null; // No path found
    }

    private List<String> aStarAlgorithm(String startWord, String endWord) {
        Map<String, Integer> costMap = new HashMap<>();
        Map<String, Integer> heuristicMap = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new PriorityQueue<>(Comparator.comparingInt((String w) -> costMap.get(w) + heuristicMap.get(w)));
        Map<String, Integer> nodeLength = new HashMap<>(); // Map untuk menyimpan panjang node

        queue.add(startWord);
        costMap.put(startWord, 0);
        heuristicMap.put(startWord, calculateHeuristic(startWord, endWord));
        nodeLength.put(startWord, 1); // Panjang node awal adalah 1

        while (!queue.isEmpty()) {
            String currentWord = queue.poll();
            if (currentWord.equals(endWord)) {
                return reconstructPath(parentMap, endWord);
            }

            List<String> neighbors = getNeighbors(currentWord);
            for (String neighbor : neighbors) {
                int newCost = costMap.get(currentWord) + 1;
                if (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor)) {
                    costMap.put(neighbor, newCost);
                    heuristicMap.put(neighbor, calculateHeuristic(neighbor, endWord));
                    parentMap.put(neighbor, currentWord);
                    queue.add(neighbor);
                    nodeLength.put(neighbor, nodeLength.get(currentWord) + 1); // Panjang node diupdate
                }
            }
        }

        return null; // No path found
    }

    private List<String> reconstructPath(Map<String, String> parentMap, String endWord) {
        List<String> path = new ArrayList<>();
        String currentWord = endWord;
        while (currentWord != null) {
            path.add(0, currentWord);
            currentWord = parentMap.get(currentWord);
        }
        return path;
    }

    private int calculateHeuristic(String word, String endWord) {
        int heuristic = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                heuristic++;
            }
        }
        return heuristic;
    }

    private List<String> getNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        for (String dictWord : dictionary) {
            if (isNeighbor(word, dictWord)) {
                neighbors.add(dictWord);
            }
        }
        return neighbors;
    }

    private boolean isNeighbor(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }
        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
            }
            if (diffCount > 1) {
                return false;
            }
        }
        return diffCount == 1;
    }
}
