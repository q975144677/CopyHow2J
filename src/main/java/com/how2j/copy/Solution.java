package com.how2j.copy;
public class Solution {
    public int FindGreatestSumOfSubArray(int[] array) {
        if(array.length == 1){
            return array[0];
        }

        int dp[] = new int[array.length];
        dp[0] = array[0] <=0?0:array[0];
        int max = dp[0];
        for(int i = 1; i < dp.length ; i ++){
          if(dp[i-1] + array[i] >= 0){
              dp[i] = dp[i-1]+array[i];
          }
          else{
              dp[i] = Integer.MIN_VALUE ;
          }
        if(max < dp[i]){
              max = dp[i];
        }
        }


    return max;
    }

}