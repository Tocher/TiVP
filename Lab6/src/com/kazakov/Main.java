package com.kazakov;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(primeTest(GetPrimeNumber()));
    }

    public static boolean primeTest(BigInteger w) {
        int i = 0;
        int n = 50 + (int) Math.round(Math.random() * 50);
        BigInteger m;
        BigInteger wView = w.subtract(BigInteger.ONE);
        int a = 0;
        BigInteger wTemp = wView;
        while (wTemp.mod(BigInteger.ONE.add(BigInteger.ONE)) == BigInteger.ZERO) {
            wTemp = wTemp.divide(BigInteger.ONE.add(BigInteger.ONE));
            a++;
        }
        m = wTemp;

        while(i < n) {
            // Step 3
            BigInteger b = rndBigInt(w);
            // Step 4
            int j = 0;
            BigInteger z = BIpow(b, m).mod(w);

            while(j < a) {
                // Step 5
                if ((j == 0 && z.equals(BigInteger.ONE)) || z.equals(wView)) {
                    break;
                }
                // Step 6
                if (j > 0 && z.equals(BigInteger.ONE)) {
                    return false; // Step 8
                }
                // Step 7
                j++;
                if (j >= a)
                    return false;
                z = z.pow(2).mod(w);
            }
            i++;
        }

        return true;
    }

    public static BigInteger rndBigInt(BigInteger max) {
        Random rnd = new Random();
        do {
            BigInteger i = new BigInteger(max.bitLength(), rnd);
            if (i.compareTo(max) < 0)
                return i;
        } while (true);
    }

    public static BigInteger BIpow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0) {
            if (exponent.testBit(0)) result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static int GetSEED(int length) {
        byte[] b = new byte[length];
        new Random().nextBytes(b);
        return new BigInteger(b).intValue();
    }

    public static BigInteger GetPrimeNumber() throws NoSuchAlgorithmException {
        int byteSize = 20;
        int g = byteSize*8;

        BigInteger SEED = BigInteger.valueOf(GetSEED(byteSize));
        BigInteger nSEED = SEED.add(BigInteger.ONE);
        BigInteger devider = BigInteger.valueOf((long) Math.pow(2,g));
        BigInteger max = BigInteger.valueOf((long) Math.pow(2,g-1));

        BigInteger SEED_sha = new BigInteger(sha1(SEED.toString()).getBytes());
        BigInteger nSEED_sha = new BigInteger(sha1(nSEED.mod(devider).toString()).getBytes());

        // Step 2
        BigInteger U = SEED_sha.xor(nSEED_sha);

        // Step 3
        BigInteger q = U.or(BigInteger.ONE).or(max);
        return q;
    }
}
