package exercise;

import java.util.*;

public class Problem9_TwoSum {

    public static List<int[]> findTwoSum(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (int num : nums) {
            int complement = target - num;

            if (map.containsKey(complement)) {
                result.add(new int[]{complement, num});
            }

            map.put(num, 1);
        }

        return result;
    }

    public static void solve() {

        int[] nums = {500, 300, 200, 700};
        int target = 500;

        List<int[]> pairs = findTwoSum(nums, target);

        for (int[] pair : pairs) {
            System.out.println(pair[0] + " + " + pair[1] + " = " + target);
        }
    }
}
