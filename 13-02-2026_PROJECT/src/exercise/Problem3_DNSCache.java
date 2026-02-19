package exercise;

import java.util.*;
import java.util.concurrent.*;

public class Problem3_DNSCache {

    static class DNSEntry {
        String domain;
        String ip;
        long expiryTime;

        DNSEntry(String domain, String ip, long ttlSeconds) {
            this.domain = domain;
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private final int capacity;
    private final LinkedHashMap<String, DNSEntry> cache;
    private int hits = 0;
    private int misses = 0;

    public Problem3_DNSCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > Problem3_DNSCache.this.capacity;
            }
        };

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                this::cleanupExpiredEntries, 5, 5, TimeUnit.SECONDS
        );
    }

    public synchronized String resolve(String domain) {
        DNSEntry entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            return "Cache HIT → " + entry.ip;
        }

        misses++;
        String ip = queryUpstream(domain);
        cache.put(domain, new DNSEntry(domain, ip, 10));
        return "Cache MISS → " + ip;
    }

    private String queryUpstream(String domain) {
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        return "172.217." + new Random().nextInt(255) + "." + new Random().nextInt(255);
    }

    private synchronized void cleanupExpiredEntries() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    public String getStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0 / total);
        return "Hit Rate: " + String.format("%.2f", hitRate) + "%";
    }

    public static void solve() {
        Problem3_DNSCache dns = new Problem3_DNSCache(3);

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.getStats());
    }
}
