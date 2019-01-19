package br.com.omniatechnology.pernavendas.pernavendas.utils;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by PauLinHo on 19/08/2017.
 */

public abstract class EncryptMD5Util {

    /**
     *
     * @param senha
     *            Recebe uma String como parametro e Encriptar com MD5
     * @return Um string encriptada
     */
    public static String getEncryptMD5(String senha) {

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new String(Hex.encodeHex(md.digest(senha.getBytes())));

//        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
//        return hash.toString(16);

    }

    private static String getString( byte[] bytes )
    {
        StringBuffer sb = new StringBuffer();
        for( int i=0; i<bytes.length; i++ )
        {
            byte b = bytes[ i ];
            String hex = Integer.toHexString((int) 0x00FF & b);
            if (hex.length() == 1)
            {
                sb.append("0");
            }
            sb.append( hex );
        }
        return sb.toString();
    }
}
