package leetcode.array;

import org.junit.Test;

/**
 * Remove Duplicates from Sorted Array
 * Question come from:
 * https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/727/
 */
public class TestDuplicat {

    @Test
    public void test1(){
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        int len = removeDuplicates(nums);
        System.out.println(len);
        for (int i = 0; i < len; i++){
            System.out.print(nums[i]+" ");
        }
        System.out.println();
    }

    private int removeDuplicates(int[] nums) {
        int len = nums.length;
        int counts = 0;
        if(len <= 0)
            return counts;
        int temp = nums[0];
        counts++;
        for (int i = 1; i < len; i++){
            if((temp^nums[i]) != 0){
                temp = nums[i];
                if(i > counts){
                    nums[counts] = temp;
                }
                counts++;
            }
        }
        return counts;
    }
}
