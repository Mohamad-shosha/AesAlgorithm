# AESAlgorithm
This program implements AES encryption and decryption for image files using three AES modes: ECB (Electronic Codebook), CBC (Cipher Block Chaining), and CTR (Counter Mode). It allows the user to encrypt an image file, store the encrypted data in a new file, and save the encryption key and initialization vector (IV) for later decryption. The user can also decrypt an encrypted image using the stored key and IV.

### Key Features:
- **Key Generation**: The program generates a 256-bit AES key using `KeyGenerator`.
- **Initialization Vector (IV) Generation**: For CBC and CTR modes, a 16-byte IV is generated using `SecureRandom`.
- **Encryption**: The program supports AES encryption in three modes:
  - **ECB Mode**: A simpler encryption mode where each block is encrypted independently.
  - **CBC Mode**: A more secure encryption mode where each block is XORed with the previous block’s ciphertext before encryption.
  - **CTR Mode**: A mode that converts AES into a stream cipher, where encryption is done by generating a keystream from a counter.
- **Decryption**: The program decrypts the encrypted image using the corresponding AES mode and the stored key and IV.
- **File Handling**: The program reads an image file, encrypts it, and writes the encrypted data to a file with the `.enc` extension. It also reads the encrypted file, decrypts it, and writes the decrypted image to a `.jpg` file.

### Workflow:
1. **Encryption**:
   - The user selects the AES mode (ECB, CBC, or CTR).
   - A random AES key and IV (for CBC and CTR modes) are generated.
   - The image file is read, encrypted, and saved to an encrypted file.
   - The encryption key and IV are stored in a separate file (`keyAndIv.dat`) for future decryption.

2. **Decryption**:
   - The user uploads the encrypted image file (`encryptedImage.enc`).
   - The program reads the key and IV from the `keyAndIv.dat` file.
   - The encrypted data is decrypted using the selected AES mode and the saved key and IV.
   - The decrypted image is saved as a `.jpg` file.

### Use Cases:
- **Image Encryption**: Securely encrypting image files to protect sensitive data.
- **Image Decryption**: Decrypting the encrypted image files back to their original form.

This program provides a simple and effective way to securely encrypt and decrypt images using AES encryption in various modes, helping users protect their data with cryptographic techniques.
