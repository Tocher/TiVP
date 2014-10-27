package com.kazakov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str_n = br.readLine();
        String str_k = br.readLine();

        int n = 0;
        int k = 0;

        try {
            n = Integer.parseInt(str_n);
            k = Integer.parseInt(str_k);
        }
        catch (Exception e) {
            System.out.println("Invalid number(s)!");
            return;
        }

        if ( (3 < n && n < 20) && (2 < k && k < 19) ) {
            String secret = br.readLine();
            Algoritm alg = new Algoritm(new BigInteger(secret), k, n);
            BigInteger Secret = alg.restoreSecret();
            System.out.println("Secret: " + Secret);
        }
        else {
            System.out.println("3 < n < 20; 2 < k < 19");
        }
    }
}
