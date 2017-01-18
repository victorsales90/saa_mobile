package br.unifor.pin.saa.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe responsavel por encriptar a senha do usuario.
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */

public class Encripta {

    /**
     * Metodo estatico responsavel por encriptar a senha informada pelo usuario no momento do login.
     * @param original
     * @return senha criptografada
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encripta (String original) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            String senha = hexString.toString();
            return senha;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ns) {
            ns.printStackTrace();
            return null;
        }
    }

}
