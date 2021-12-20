package algo_questions;

import java.util.Arrays;

public class Solutions {

    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time slots. A task can
     * only be completed in a time slot if the length of the time slot is grater than the
     * no. of hours needed to complete the task.
     *
     * @param tasks     array of integers of length n. tasks[i] is the time in hours required to complete task i.
     * @param timeSlots array of integersof length m. timeSlots[i] is the length in hours of the slot i.
     * @return maximal amount of tasks that can be completed
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots) {

        var i = tasks.length - 1;
        var j = timeSlots.length - 1;

        // Sort tasks and timeSlots in decreasing order
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        var maxCounter = 0;
        // looping over tasks array from end to start
        while ((-1 < i) && (-1 < j)) {
            // checking restriction
            while (tasks[i] > timeSlots[j]) {
                --i;
                if (i < 0) {break;}
            }

            // assign task to time slot
            if ((i >= 0 ) && (tasks[i] <= timeSlots[j])) {
                ++maxCounter;
                --i;
                --j;
            }

        }
        return maxCounter;
        // Time complexity ->  O(nlogn + mlogm)
    }

    /**
     * Method computing the nim amount of leaps a frog needs to jumb across n waterlily leaves,
     * from leaf 1 to leaf n. The leaves vary in size and how stable they are, so some leaves allow larger
     * leaps than others. leapNum[i] is an integer telling you how many leaves ahead you can jump from leaf i.
     * If leapNum[3]=4, the frog can jump from leaf 3, and land on any of the leaves 4, 5, 6 or 7.
     * @param leapNum array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return minimal no. of leaps to last leaf.
     */

    public static int minLeap(int[] leapNum)
    {

        var n = leapNum.length - 1;
        if (n <= 1)
        {
            return 0;
        }

        var maxReach = leapNum[0];
        var counter = leapNum[0];
        var result = 0;

        for (int i = 1; i < n; i++) {
            if (maxReach < i + leapNum[i])
            {
                maxReach = i + leapNum[i];
            }
            --counter;
            if (counter == 0)
            {
                ++result;
                counter = maxReach -  i;
            }
        }
        return result + 1;
    }


    /**
     * Method computing the solution to the following problem: A boy is filling the water trough for his father's
     * cows in their village.
     * The trough holds n liters of water. With every trip to the village well, he can return using either
     * the 2 bucket yoke, or simply with a single bucket. A bucket holds 1 liter.
     * In how many different ways can he fill the water trough? n can be assumed to be greater or equal to 0,
     * less than or equal to 48.
     * @param n
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n)
    {
        // buckets[i] should contain the output of bucketWalk(i)
        int[] buckets = new int[48];
        buckets[0] = 1;
        buckets[1] = 1;
        buckets[2] = 2;

        for (int i = 3; i <= n; i++) {
            buckets[i] = buckets[i-1] + buckets[i-2];
        }

        // output
        return buckets[n];
    }

    /**
     *     Method computing the solution to the following problem: Given an integer n, return the number of structurally unique BST's
     *     (binary search trees) which has exactly n nodes of unique values from 1 to n. You can assume n is at least 1 and at most 19.
     *     (Definition: two trees S and T are structurally distinct if one can not be obtained from the other by renaming of the nodes.) (credit: LeetCode)
     */

    public static int numTrees(int n)
    {
        int[] numOfTrees= new int[n+1];
        if (n == 0){return 0;}
        numOfTrees[0] = 1;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                numOfTrees[i] += numOfTrees[j] * numOfTrees[i-j-1];
            }

        }
        return numOfTrees[n];
    }

}
