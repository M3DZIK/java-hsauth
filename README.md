# Handshake authentication

[HSAuth](https://hsauth.medzik.dev) is a user authentication algorithm created to 
eliminate the need to send the password or even its hash to the server. 
This makes authentication more secure because the password never touches the server.

## Getting started

First, add the library as a dependency to your maven project.

```xml
<dependency>
    <groupId>dev.medzik</groupId>
    <artifactId>hsauth</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Generate keypair for server

```java
import dev.medzik.libcrypto.X25519;

byte[] serverPrivateKey = X25519.generatePrivateKey();
byte[] serverPublicKey = X25519.publicFromPrivate(serverPrivateKey);
```

### Compute private key of a user

```java
import dev.medzik.libcrypto.Hex;
import dev.medzik.libcrypto.X25519;

byte[] userPrivateKey = Hex.decode("password hash");
byte[] userPublicKey = X25519.publicFromPrivate(serverPrivateKey);
```

### Calculate the HSAuth Key

```java
import dev.medzik.hsauth.HSAuth;
import dev.medzik.hsauth.HSAuthVersion;

HSAuth hsAuth = new HSAuth(HSAuthVersion.V1);
String hsAuthKey = hsauth.generateKey(userPrivateKey, serverPublicKey);
```

### Validate the HSAuth Key on the server

```java
import dev.medzik.hsauth.HSAuth;

HSAuth hsAuth = new HSAuth(HSAuthVersion.V1);
boolean valid = hsAuth.isValid(hsAuthKey, serverPrivateKey, userPublicKey);
```
