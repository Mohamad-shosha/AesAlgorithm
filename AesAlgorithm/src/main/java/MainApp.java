import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AES Image Encryption");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JLabel statusLabel = new JLabel("Select an image and encryption mode.");
            statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(statusLabel, BorderLayout.SOUTH);

            JComboBox<String> encryptionModeComboBox = new JComboBox<>(new String[]{"ECB", "CBC", "CTR"});
            JButton uploadButton = new JButton("Upload Image");
            JButton encryptButton = new JButton("Encrypt Image");
            JButton decryptButton = new JButton("Decrypt Image");

            JPanel topPanel = new JPanel();
            topPanel.add(new JLabel("Select Encryption Mode:"));
            topPanel.add(encryptionModeComboBox);
            topPanel.add(uploadButton);
            topPanel.add(encryptButton);
            topPanel.add(decryptButton);
            panel.add(topPanel, BorderLayout.NORTH);

            frame.add(panel);

            // Event Handlers
            final File[] selectedFile = {null};
            uploadButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile[0] = fileChooser.getSelectedFile();
                    statusLabel.setText("Selected file: " + selectedFile[0].getName());
                }
            });

            encryptButton.addActionListener(e -> {
                if (selectedFile[0] == null) {
                    JOptionPane.showMessageDialog(frame, "Please upload an image first.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    String mode = (String) encryptionModeComboBox.getSelectedItem();
                    AESProcessor.encryptImage(selectedFile[0], mode);
                    statusLabel.setText("Image encrypted successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error during encryption: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            decryptButton.addActionListener(e -> {
                if (selectedFile[0] == null) {
                    JOptionPane.showMessageDialog(frame, "Please upload an encrypted image first.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    AESProcessor.decryptImage(new File("encryptedImage.enc"));
                    statusLabel.setText("Image decrypted successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error during decryption: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.setVisible(true);
        });
    }
}
