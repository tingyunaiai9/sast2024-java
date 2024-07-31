package homework;

import java.util.Scanner;
import java.util.Arrays;

public class ThreeSumClosest {
    public static int threeSumClosest(int[] nums, int target) {
        int n = nums.length, ans = 0;
        // TODO begin

        Arrays.sort(nums);
        int closest = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int left = i + 1;
            int right = n - 1;

            while (right > left) {
                int sum = nums[i] + nums[left] + nums[right];
                int diff = Math.abs(sum - target);
                if (diff < closest) {
                    closest = diff;
                    ans = sum;
                }

                if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        // TODO end
        return ans;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int size;
        System.out.print("Enter the size of the array: ");
        size = input.nextInt();
        int[] nums = new int[size];
        System.out.print("Enter the array, separated by a space: ");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = input.nextInt();
        }
        System.out.print("Enter the target: ");
        int target = input.nextInt();
        System.out.println(threeSumClosest(nums, target));
    }
}
