import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
 
public class Login extends JFrame implements ActionListener {
 JPanel panel;
 JLabel emailLabel, passwordLabel;
 JTextField emailTF;
 JPasswordField passwordTF;
 JButton loginBtn, registerBtn;
 
 public Login() {
 super("Login");
 this.setBounds(600, 200, 800, 500);
 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
 Font myFont1 = new Font("Arial", Font.PLAIN, 14);
 Font myFont2 = new Font("Arial", Font.BOLD, 14);
 
 panel = new JPanel() {
 @Override
 protected void paintComponent(Graphics g) {
 super.paintComponent(g);
 ImageIcon imageIcon = new ImageIcon("image/Media.jpg");
 Image img = imageIcon.getImage();
 g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
 }
 };
 panel.setLayout(null);
 
 emailLabel = new JLabel("Car Number:");
 emailLabel.setBounds(230, 150, 100, 30);
 emailLabel.setForeground(Color.BLACK);
 emailLabel.setFont(myFont1);
 panel.add(emailLabel);
 
 emailTF = new JTextField();
 emailTF.setBounds(300, 150, 200, 30);
 panel.add(emailTF);
 
 passwordLabel = new JLabel("Password:");
 passwordLabel.setBounds(230, 200, 100, 30);
 passwordLabel.setForeground(Color.BLACK);
 passwordLabel.setFont(myFont1);
 panel.add(passwordLabel);
 
 passwordTF = new JPasswordField();
 passwordTF.setBounds(300, 200, 200, 30);
 panel.add(passwordTF);
 
 loginBtn = new JButton("Login");
 loginBtn.setBounds(300, 260, 90, 30);
 loginBtn.setBackground(new Color(255, 146, 105));
 loginBtn.setForeground(Color.WHITE);
 loginBtn.setFont(myFont2);
 loginBtn.addActionListener(this);
 panel.add(loginBtn);
 
 registerBtn = new JButton("Register");
 registerBtn.setBounds(400, 260, 100, 30);
 registerBtn.setBackground(new Color(255, 146, 105));
 registerBtn.setForeground(Color.WHITE);
 registerBtn.setFont(myFont2);
 registerBtn.addActionListener(this);
 panel.add(registerBtn);
 
 this.add(panel);
 }
 
 public void actionPerformed(ActionEvent ae) {
 if (ae.getSource() == loginBtn) {
 String email = emailTF.getText();
 String password = new String(passwordTF.getPassword());
 
 if (email.isEmpty() || password.isEmpty()) {
 JOptionPane.showMessageDialog(this, "Please Enter your Car Nmber and password!", "Error", JOptionPane.ERROR_MESSAGE);
 } else if (!isValidCredentials(email, password)) {
 JOptionPane.showMessageDialog(this, "Incorrect Car Number or Password!", "Error", JOptionPane.ERROR_MESSAGE);
 } else {
 JOptionPane.showMessageDialog(this, "Login Successful!");
 this.setVisible(false);
 FrameIntro frame = new FrameIntro();
 frame.setVisible(true);
 }
 } else if (ae.getSource() == registerBtn) {
 this.setVisible(false);
 Register register = new Register();
 register.setVisible(true);
 }
 }
 private boolean isValidCredentials(String email, String password) {
 try (BufferedReader br = new BufferedReader(new FileReader("registration_data.txt"))) {
 String line;
 while ((line = br.readLine()) != null) {
 String[] parts = line.split(","); // Assuming CSV format
 if (parts.length == 2) {
 String storedEmail = parts[0].trim();
 String storedPassword = parts[1].trim();
 if (storedEmail.equals(email) && storedPassword.equals(hashPassword(password))) {
 return true; // Valid credentials found
 }
 }
 }
 } catch (IOException e) {
 e.printStackTrace();
 }
 return false; // No valid credentials found
 }
 private String hashPassword(String password) {
 try {
 MessageDigest md = MessageDigest.getInstance("SHA-256");
 byte[] hashedBytes = md.digest(password.getBytes());
 StringBuilder sb = new StringBuilder();
 for (byte b : hashedBytes) {
 sb.append(String.format("%02x", b));
 }
 return sb.toString();
 } catch (NoSuchAlgorithmException e) {
 e.printStackTrace();
 return null;
 }
 }
}