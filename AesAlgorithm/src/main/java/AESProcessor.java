import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.SecureRandom;

public class AESProcessor {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_CBC_MODE = "AES/CBC/PKCS5Padding";
    private static final String AES_ECB_MODE = "AES/ECB/PKCS5Padding";
    private static final String AES_CTR_MODE = "AES/CTR/NoPadding";

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static void encryptImage(File imageFile, String mode) throws Exception {
        SecretKey secretKey = generateKey();
        IvParameterSpec iv = null;

        if (mode.equals("CBC") || mode.equals("CTR")) {
            iv = generateIv();
        }

        byte[] imageBytes = readFileToByteArray(imageFile);

        Cipher cipher = null;
        if (mode.equals("ECB")) {
            cipher = Cipher.getInstance(AES_ECB_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else if (mode.equals("CBC") || mode.equals("CTR")) {
            String cipherMode = mode.equals("CBC") ? AES_CBC_MODE : AES_CTR_MODE;
            cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        }

        byte[] encryptedData = cipher.doFinal(imageBytes);

        try (FileOutputStream fileOut = new FileOutputStream("encryptedImage.enc")) {
            fileOut.write(encryptedData);
        }

        try (ObjectOutputStream keyOut = new ObjectOutputStream(new FileOutputStream("keyAndIv.dat"))) {
            keyOut.writeObject(secretKey);
            if (iv != null) {
                keyOut.writeObject(iv);
            }
        }
    }

    public static void decryptImage(File encryptedFile) throws Exception {
        ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("keyAndIv.dat"));
        SecretKey secretKey = (SecretKey) keyIn.readObject();
        IvParameterSpec iv = null;
        try {
            iv = (IvParameterSpec) keyIn.readObject();
        } catch (Exception e) {
        }
        keyIn.close();

        byte[] encryptedData = readFileToByteArray(encryptedFile);

        Cipher cipher = null;
        if (iv != null) {
            cipher = Cipher.getInstance(AES_CBC_MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        } else {
            cipher = Cipher.getInstance(AES_ECB_MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }

        byte[] decryptedData = cipher.doFinal(encryptedData);

        try (FileOutputStream fileOut = new FileOutputStream("decryptedImage.jpg")) {
            fileOut.write(decryptedData);
        }
    }

    private static byte[] readFileToByteArray(File file) throws IOException {
        byte[] fileBytes = new byte[(int) file.length()];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(fileBytes);
        }
        return fileBytes;
    }
}