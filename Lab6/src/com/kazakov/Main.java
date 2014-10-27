package com.kazakov;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        BigInteger temp = GetPrimeNumber();
        while(!isPrime(temp, 20)) {
            temp = GetPrimeNumber();
        }
        System.out.println(temp);
    }

    public static boolean isPrime(BigInteger m, int r) {
        BigInteger t = m.subtract(BigInteger.ONE);
        BigInteger two = BigInteger.ONE.add(BigInteger.ONE);

        int s = 0;
        if(t.mod(two).equals(BigInteger.ZERO)) {
            while (t.mod(two).equals(BigInteger.ZERO)) {
                t = t.divide(two);
                s++;
            }
        }

        for(int i = 0; i < r; i++) {
            BigInteger a = rndBigInt(m.subtract(two));
            BigInteger x = a.modPow(t,m);
            if(!x.equals(BigInteger.ONE) && !x.equals(m.subtract(BigInteger.ONE))) {
                int j = 0;
                boolean flag = true;
                while(j < s - 1) {
                    x = x.multiply(x).mod(m);
                    if(x.equals(BigInteger.ONE))
                        return false;
                    if(x.equals(m.subtract(BigInteger.ONE))) {
                        flag = false;
                        break;
                    }
                }
                if(flag)
                    return false;
            }
        }

        return true;
    }

    public static BigInteger rndBigInt(BigInteger max) {
        Random rnd = new Random();
        do {
            BigInteger i = new BigInteger(max.bitLength(), rnd);
            if (i.compareTo(max) < 0 && i.compareTo(BigInteger.ONE) > 0)
                return i;
        } while (true);
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

    public static BigInteger GetSEED(int length) {
        byte[] b = new byte[length];
        new Random().nextBytes(b);
        return new BigInteger(b);
    }

    public static BigInteger GetPrimeNumber() throws NoSuchAlgorithmException {
        int byteSize = 20;
        int g = byteSize*8;

        BigInteger SEED = GetSEED(byteSize);
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
