package exercise;

import java.util.*;

public class Problem6_RateLimiter {

    static class TokenBucket {
        int tokens;
        long lastRefillTime;
        final int maxTokens = 1000;

        TokenBucket() {
            tokens = maxTokens;
            lastRefillTime = System.currentTimeMillis();
        }

        synchronized boolean allowRequest() {
            long now = System.currentTimeMillis();
            long elapsed = now - lastRefillTime;

            if (elapsed >= 3600000) {
                tokens = maxTokens;
                lastRefillTime = now;
            }

            if (tokens > 0) {
                tokens--;
                return true;
            }

            return false;
        }

        int remainingTokens() {
            return tokens;
        }
    }

    private final Map<String, TokenBucket> clients = new HashMap<>();

    public boolean checkRateLimit(String clientId) {
        clients.putIfAbsent(clientId, new TokenBucket());
        return clients.get(clientId).allowRequest();
    }

    public int getRemaining(String clientId) {
        if (!clients.containsKey(clientId)) return 1000;
        return clients.get(clientId).remainingTokens();
    }

    public static void solve() {

        Problem6_RateLimiter limiter = new Problem6_RateLimiter();
        String client = "abc123";

        for (int i = 0; i < 5; i++) {
            boolean allowed = limiter.checkRateLimit(client);
            System.out.println("Request " + (i + 1) + ": "
                    + (allowed ? "Allowed" : "Denied")
                    + " (" + limiter.getRemaining(client)
                    + " remaining)");
        }
    }
}
