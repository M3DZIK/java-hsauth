package dev.medzik.hsauth;

import dev.medzik.libcrypto.Hex;
import dev.medzik.libcrypto.X25519;

import java.security.InvalidKeyException;
import java.util.Arrays;

class HSAuthV1 {
    public static byte[] generateKey(byte[] userPrivateKey, byte[] serverPublicKey) throws InvalidKeyException {
        return X25519.computeSharedSecret(userPrivateKey, serverPublicKey);
    }

    public static boolean isValid(byte[] hsAuthKey, byte[] serverPrivateKey, byte[] userPublicKey) {
        try {
            byte[] sharedKey = X25519.computeSharedSecret(serverPrivateKey, userPublicKey);
            // compare two byte arrays
            return Arrays.equals(hsAuthKey, sharedKey);
        } catch (InvalidKeyException e) {
            return false;
        }
    }
}
