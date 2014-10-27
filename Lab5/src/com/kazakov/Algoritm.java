package com.kazakov;

import java.math.BigInteger;
import java.util.Random;

public class Algoritm {
    private int K;
    private int N;

    private BigInteger p;
    private BigInteger M;
    private BigInteger M_new;
    private BigInteger[] d;
    private BigInteger[] k; //Доли

    public Algoritm(BigInteger secret, int k, int n) {
        this.M = secret;
        this.K = k;
        this.N = n;

        this.p = M.nextProbablePrime();
        Сoprime(p);

        int r = (int) (Math.random() * 100 + 1);

        M_new = M.add(p.multiply(BigInteger.valueOf(r)));

        GetPortions();

        //Tests
        System.out.println("M=" + M + " p=" + p + " k=" + K + " n=" + N + " m`=" + M_new + " r=" + r);
        for(int i = 0; i < N; i++)
            System.out.println("D"+i+" = "+d[i]+"   K"+i+" = "+this.k[i]);

    }

    // китайская теорема об остатках
    public BigInteger restoreSecret() {
        System.out.println("STEP1");
        // Step 1
        BigInteger M1 = d[0];
        for(int i = 1; i < K; i++)
            M1 = M1.multiply(d[i]);
        // Step 2
        System.out.println("STEP2");
        BigInteger[] Mi = new BigInteger[K];
        for(int i = 0; i < K; i++) {
            Mi[i] = M1.divide(d[i]);
        }
        // Step 3
        System.out.println("STEP3");
        BigInteger[] MiMinus = new BigInteger[K];
        for (int i = 0; i < K; i++) {
            BigInteger[] vals = extendedEuclidGcd(Mi[i], d[i]);
            MiMinus[i] = vals[1];
        }

        // Step 4
        System.out.println("STEP4");
        BigInteger result = BigInteger.ZERO;
        for(int i = 0; i < K; i++) {
            result = result.add(k[i].multiply(Mi[i].multiply(MiMinus[i])));
            System.out.println("d=" + d[i] + " k=" + k[i] + " Mi="+Mi[i]+" Mi^-1="+MiMinus[i]);
        }
        result = result.mod(M1);
        System.out.println("M` = "+result);
        return result.mod(p);
    }

    // Схема Асмута — Блума
    public void GetPortions() {
        this.k = new BigInteger[N];

        for(int i = 0; i < N; i++) {
            k[i] = M_new.mod(d[i]);
        }
    }

    public void Сoprime(BigInteger first) {
        this.d = new BigInteger[N];
        d[0] = first.nextProbablePrime();

        for(int i = 1; i < N; i++) {
            d[i] = d[i-1].nextProbablePrime();
        }
        if(!CheckСoprime())
            Сoprime(d[0]);
    }

    public boolean CheckСoprime() {
        BigInteger temp1 = d[0];
        for(int i = 1; i < K; i++)
            temp1 = temp1.multiply(d[i]);
        BigInteger temp2 = p;
        for(int i = N-K+1; i < N; i++)
            temp2 = temp2.multiply(d[i]);

        if(temp1.compareTo(temp2) == 1)
            return true;

        return false;
    }

    private BigInteger[] extendedEuclidGcd(BigInteger p, BigInteger q) {
        if (q.compareTo(BigInteger.ZERO) == 0)      //q == 0
            return new BigInteger[] { p, BigInteger.ONE, BigInteger.ZERO };

        BigInteger[] vals = extendedEuclidGcd(q, p.mod(q));
        BigInteger d = vals[0];
        BigInteger a = vals[2];
        BigInteger b = vals[1].subtract(p.divide(q).multiply(vals[2]));
        return new BigInteger[] { d, a, b };
    }
}
