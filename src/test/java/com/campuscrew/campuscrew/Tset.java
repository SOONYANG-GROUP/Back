package com.campuscrew.campuscrew;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Tset {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(reader.readLine());
        String[] arrList = reader.readLine().split("");
        int find = Integer.parseInt(reader.readLine());
        int i = 0;
        int count = 0;
        int arr[] = new int[len];

        for (String s : arrList) {
            arr[i++] = Integer.parseInt(s);
        }

        for (int j = 0; j < len; j++) {
            if(arr[j] == find) {
                count++;
            }
        }
        System.out.println(count);
        reader.close();
    }

}
