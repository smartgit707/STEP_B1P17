package exercise;

import java.util.*;

public class Problem4_PlagiarismDetector {

    private final Map<String, Set<String>> index = new HashMap<>();
    private final Map<String, List<String>> documents = new HashMap<>();
    private final int n = 5;

    public void addDocument(String docId, String text) {
        List<String> grams = extractNGrams(text);
        documents.put(docId, grams);
        for (String gram : grams) {
            index.computeIfAbsent(gram, k -> new HashSet<>()).add(docId);
        }
    }

    private List<String> extractNGrams(String text) {
        String[] words = text.split("\\s+");
        List<String> grams = new ArrayList<>();
        for (int i = 0; i <= words.length - n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++)
                sb.append(words[i + j]).append(" ");
            grams.add(sb.toString().trim());
        }
        return grams;
    }

    public Map<String, Double> analyze(String docId) {
        Map<String, Integer> matchCount = new HashMap<>();
        List<String> grams = documents.get(docId);

        for (String gram : grams) {
            Set<String> docs = index.getOrDefault(gram, Collections.emptySet());
            for (String other : docs) {
                if (!other.equals(docId)) {
                    matchCount.put(other,
                            matchCount.getOrDefault(other, 0) + 1);
                }
            }
        }

        Map<String, Double> similarity = new HashMap<>();
        for (String other : matchCount.keySet()) {
            similarity.put(other,
                    matchCount.get(other) * 100.0 / grams.size());
        }

        return similarity;
    }

    public static void solve() {
        Problem4_PlagiarismDetector p =
                new Problem4_PlagiarismDetector();

        p.addDocument("doc1",
                "java is powerful and java is fast and java is scalable");

        p.addDocument("doc2",
                "java is powerful and java is secure and scalable");

        p.addDocument("doc3",
                "python is easy and readable language");

        System.out.println(p.analyze("doc1"));
    }
}
