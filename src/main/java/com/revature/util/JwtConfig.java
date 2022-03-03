package com.revature.util;



import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.Key;
import java.util.Properties;

public class JwtConfig {

    Properties myprops = new Properties();
    String salt;
    private int expiration = 60 * 60 * 1000;        // 1000 ms-sec, 60 sec-min, 60 min-hour
    private final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
    private final Key signingKey;

    public JwtConfig(){

        try {
            //this.myprops.load(new FileReader("src/main/resources/application.properties"));
            this.myprops.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("application.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
        this.salt = myprops.getProperty("db-salty-byte");

        byte[] saltyBytes = DatatypeConverter.parseBase64Binary(salt);
        signingKey = new SecretKeySpec(saltyBytes, sigAlg.getJcaName());

    }

    public int getExpiratation() {return expiration;}

    public SignatureAlgorithm getSigAlg(){
        return sigAlg;
    }

    public Key getSigningKey() {
        return signingKey;
    }
}
