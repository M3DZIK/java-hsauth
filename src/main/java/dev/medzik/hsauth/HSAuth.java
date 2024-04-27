package dev.medzik.hsauth;

import dev.medzik.libcrypto.Hex;

import java.security.InvalidKeyException;

public class HSAuth {
    private final HSAuthVersion version;

    public HSAuth(HSAuthVersion version) {
        this.version = version;
    }

    /**
     * Generates a new HSAuth key between the user and the server.
     *
     * @param privateKey the user private key
     * @param serverPublicKey the server public key
     * @return the generated HSAuth key
     * @throws InvalidKeyException if the key is invalid
     */
    public String generateKey(byte[] privateKey, byte[] serverPublicKey) throws InvalidKeyException {
        if (version == HSAuthVersion.V1) {
            return Hex.encode(HSAuthV1.generateKey(privateKey, serverPublicKey));
        }

        throw new IllegalStateException();
    }

    /**
     * Checks if the given HSAuth key is valid between the server and the user.
     *
     * @param hsAuthKey the HSAuth key
     * @param serverPrivateKey the server private key
     * @param userPublicKey the user public key
     * @return true if the key is valid, false otherwise
     */
    public boolean isValid(String hsAuthKey, byte[] serverPrivateKey, byte[] userPublicKey) {
        if (version == HSAuthVersion.V1) {
            return HSAuthV1.isValid(Hex.decode(hsAuthKey), serverPrivateKey, userPublicKey);
        }

        throw new IllegalStateException();
    }
}
