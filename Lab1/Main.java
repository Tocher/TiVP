package com.kazakov;

import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] trSides = new int[3];

        for(int i=0;i<trSides.length;i++) {
            try {
                int temp = Integer.parseInt(br.readLine());
                if(temp <= 0) {
                    System.out.println("numbers must be positive.");
                    return;
                }
                trSides[i] = temp;
            }
            catch (Exception e) {
                System.out.println("incorrect numbers.");
                return;
            }
        }


        if(
                (trSides[0] < trSides[1] + trSides[2]) &&
                (trSides[1] < trSides[0] + trSides[2]) &&
                (trSides[2] < trSides[1] + trSides[0])
                ) {

            if ((trSides[0] == trSides[1]) && (trSides[1] == trSides[2])) {
                System.out.println("triangle is equilateral.");
            } else if ((trSides[0] == trSides[1]) || (trSides[1] == trSides[2]) || (trSides[2] == trSides[0])) {
                System.out.println("triangle is isosceles.");
            } else {
                System.out.println("triangle is scalene.");
            }
        }
        else {
            System.out.println("triangle doesn't exist, wrong numbers.");
        }
    }
}
