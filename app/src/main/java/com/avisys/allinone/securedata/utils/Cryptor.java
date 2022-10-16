package com.avisys.allinone.securedata.utils;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class Cryptor {
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private byte[] iv;
    private KeyStore keyStore;
    private static final String SAMPLE_ALIAS = "MYALIAS";

    /* explicitly creation of default constructor*/
    public Cryptor(){

    }

    public void setIV() throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
       getCipherObject();
    }

    private void initKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);
    }

    public String getIV_String(){
        return Base64.encodeToString(iv,Base64.DEFAULT);
    }

    private byte[] encrypt_data(String textToEncrypt)
            throws NoSuchProviderException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = getCipherObject();
        return (cipher.doFinal(textToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    @NonNull
    public SecretKey getSecretKey_en() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return generateKey();
    }

    public SecretKey generateKey() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEY_STORE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyGenerator.init(new KeyGenParameterSpec.Builder(Cryptor.SAMPLE_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build());
        }
        return keyGenerator.generateKey();
    }

    public Cipher getCipherObject() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE,getSecretKey_en());
        iv = cipher.getIV();
        return cipher;
    }

    private String decryptData(String encrypted, byte[] encryptionIV)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException, UnrecoverableEntryException,
            KeyStoreException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128,encryptionIV);
        cipher.init(Cipher.DECRYPT_MODE,getSecretKey_de(),gcmParameterSpec);
        byte[] encryptedData = Base64.decode(encrypted, Base64.DEFAULT);
        return new String(cipher.doFinal(encryptedData),StandardCharsets.UTF_8);
    }

    private SecretKey getSecretKey_de() throws UnrecoverableEntryException,
            NoSuchAlgorithmException, KeyStoreException {
        return((KeyStore.SecretKeyEntry)keyStore.getEntry(Cryptor.SAMPLE_ALIAS,null)).getSecretKey();
    }

    public String encryptText(String string_to_encrypt){
        byte[] encryptedText= new byte[0];
        try {
            encryptedText = encrypt_data(string_to_encrypt);
            return Base64.encodeToString(encryptedText,Base64.DEFAULT);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decryptText(String encrypted,String iv){
        try {
            return decryptData(encrypted,Base64.decode(iv,Base64.DEFAULT));
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }


}
