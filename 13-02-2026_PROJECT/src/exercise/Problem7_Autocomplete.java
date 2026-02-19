package exercise;

import java.util.*;

public class Problem7_Autocomplete {

    private final Map<String, Integer> frequency = new HashMap<>();

    public void addQuery(String query) {
        frequency.put(query, frequency.getOrDefault(query, 0) + 1);
    }

    public List<String> search(String prefix) {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                pq.add(entry);
            }
        }

        List<String> result = new ArrayList<>();

        int count = 0;
        while (!pq.isEmpty() && count < 10) {
            result.add(pq.poll().getKey());
            count++;
        }

        return result;
    }

    public static void solve() {

        Problem7_Autocomplete system = new Problem7_Autocomplete();

        system.addQuery("java tutorial");
        system.addQuery("javascript basics");
        system.addQuery("java download");
        system.addQuery("java tutorial");
        system.addQuery("java 21 features");

        System.out.println(system.search("jav"));
    }
}
