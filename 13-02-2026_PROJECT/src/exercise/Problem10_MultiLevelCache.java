package exercise;

import java.util.*;

public class Problem10_MultiLevelCache {

    private final LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(10000, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                    return size() > 10000;
                }
            };

    private final Map<String, String> L2 = new HashMap<>();
    private final Map<String, String> L3 = new HashMap<>();

    public String getVideo(String id) {

        if (L1.containsKey(id)) {
            System.out.println("L1 HIT");
            return L1.get(id);
        }

        if (L2.containsKey(id)) {
            System.out.println("L2 HIT → Promoted to L1");
            String data = L2.get(id);
            L1.put(id, data);
            return data;
        }

        System.out.println("L3 HIT → Added to L2");
        String data = L3.getOrDefault(id, "VideoData");
        L2.put(id, data);
        return data;
    }

    public static void solve() {

        Problem10_MultiLevelCache cache = new Problem10_MultiLevelCache();

        cache.L3.put("video_123", "Movie A");
        cache.L3.put("video_999", "Movie B");

        cache.getVideo("video_123");
        cache.getVideo("video_123");
        cache.getVideo("video_999");
    }
}
