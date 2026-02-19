package exercise;

import java.util.*;

public class Problem5_AnalyticsDashboard {

    private final Map<String, Integer> pageViews = new HashMap<>();
    private final Map<String, Set<String>> uniqueVisitors = new HashMap<>();
    private final Map<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors
                .computeIfAbsent(url, k -> new HashSet<>())
                .add(userId);

        trafficSources.put(source,
                trafficSources.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {

        System.out.println("===== REAL-TIME DASHBOARD =====\n");
        System.out.println("Top Pages:");

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        int rank = 1;

        while (!pq.isEmpty() && rank <= 10) {
            Map.Entry<String, Integer> entry = pq.poll();
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(rank + ". " + url +
                    " - " + views +
                    " views (" + unique + " unique)");
            rank++;
        }

        System.out.println("\nTraffic Sources:");

        int totalVisits = trafficSources.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {
            double percentage =
                    entry.getValue() * 100.0 / totalVisits;

            System.out.println(entry.getKey() + ": "
                    + String.format("%.2f", percentage) + "%");
        }

        System.out.println("\n================================");
    }

    public static void solve() {

        Problem5_AnalyticsDashboard dashboard =
                new Problem5_AnalyticsDashboard();

        dashboard.processEvent("/article/breaking-news", "user1", "google");
        dashboard.processEvent("/article/breaking-news", "user2", "facebook");
        dashboard.processEvent("/sports/championship", "user3", "google");
        dashboard.processEvent("/sports/championship", "user1", "direct");
        dashboard.processEvent("/article/breaking-news", "user1", "google");

        dashboard.getDashboard();
    }
}
