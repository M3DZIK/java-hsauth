package dev.medzik.hsauth;

import dev.medzik.libcrypto.Argon2;
import dev.medzik.libcrypto.X25519;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HSAuthTest {
    @Test
    public void testHSAuthV1() throws Exception {
        byte[] serverPrivateKey = X25519.generatePrivateKey();
        byte[] serverPublicKey = X25519.publicFromPrivate(serverPrivateKey);

        Argon2 argon2 = new Argon2.Builder().build();
        byte[] userPrivateKey = argon2.hash("P@ssw0rd123!", "salt".getBytes()).getHash();
        byte[] userPublicKey =  X25519.publicFromPrivate(userPrivateKey);

        HSAuth hsAuth = new HSAuth(HSAuthVersion.V1);
        String hsAuthKey = hsAuth.generateKey(userPrivateKey, serverPublicKey);
        String hsAuthKey2 = hsAuth.generateKey(serverPrivateKey, userPublicKey);
        assertEquals(hsAuthKey, hsAuthKey2);
        assertTrue(hsAuth.isValid(hsAuthKey, serverPrivateKey, userPublicKey));
    }
}
