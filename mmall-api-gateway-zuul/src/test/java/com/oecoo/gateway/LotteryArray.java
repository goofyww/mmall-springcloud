package com.oecoo.gateway;

public class LotteryArray {

    public static void main(String[] args) {

        final int MAX = 10;
        int[][] odds = new int[MAX + 1][];

        for (int i = 0; i < odds.length; i++) {
            odds[i] = new int[i + 1];
        }

        for (int i = 0; i < odds.length; i++) {
            for (int k = 0; k < odds[i].length; k++) {

                int temp = 1;
                for (int j = 1; j <= k; j++) {
                    temp = temp * (i - j + 1) / j;
                }
                odds[i][k] = temp;
            }
        }

        for (int[] row : odds) {
            for (int v : row)
                System.out.printf("%4d",v);
            System.out.println();
        }
    }
}
