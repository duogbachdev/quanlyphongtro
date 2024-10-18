/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TroViet.Views.Login;

import TroViet.DAO.UserDAO;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author WINDOWS
 */
public class QuenMatKhau extends javax.swing.JFrame {

    /**
     * Creates new form QuenMatKhau
     */
    
    private Map<String, String> userConfirmationCodes;
    UserDAO userDao = new UserDAO();
    
    public QuenMatKhau() {
        initComponents();
        userConfirmationCodes = new HashMap<>();
        btnDoiPass.setEnabled(false);
        setLocationRelativeTo(null);
    }
    
    private void clearFields() {
        txtEmail.setText("");
        txtCode.setText("");
    }
    
    private void changePassword() {
        String email = txtEmail.getText();
        String confirmationCode = txtCode.getText();
        String newPassword = txtPassword.getText();

        // Kiểm tra mã xác nhận
        if (validateConfirmationCode(email, confirmationCode)) {
            // Lưu mật khẩu mới
            userDao.saveNewPassword(email, newPassword);
            JOptionPane.showMessageDialog(this, "Password changed successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid confirmation code.");
        }
    }

    private boolean validateConfirmationCode(String email, String confirmationCode) {

        String storedConfirmationCode = userConfirmationCodes.get(email);
        System.out.println("confirmationCode : " + storedConfirmationCode);

        return confirmationCode.equals(storedConfirmationCode);
    }

    private static String generateConfirmationCode() {
        // Tạo mã xác nhận ngẫu nhiên
        SecureRandom random = new SecureRandom();
        int confirmationCode = random.nextInt(900000) + 100000;

        // Thêm thời gian hết hạn
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(2);

        // Kết hợp mã xác nhận và thời gian hết hạn
        String confirmationCodeWithExpiration = String.format("%06d-%s",
                confirmationCode, expirationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return confirmationCodeWithExpiration.split("-")[0];
    }

    private void enableChangePasswordButton() {
        btnDoiPass.setEnabled(true);
    }

    private void sendConfirmationCode() {
        String recipientEmail = txtEmail.getText();
        String confirmationCode = generateConfirmationCode();
        System.out.println(confirmationCode);
        // Cấu hình thông tin email của bạn
        String host = "smtp.gmail.com";
//        String username = "thanhcmps26487@fpt.edu.vn";
//        String password = "galpngwtpigrkmik";
  String username = "jaycedtp@gmail.com";
        String password = "bqdcucojokakrayw";
        // Cấu hình các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        // Xác thực và gửi email
        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(username));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            emailMessage.setSubject("Confirmation Code");
            emailMessage.setText("Your confirmation code is: " + confirmationCode);

            Transport.send(emailMessage);

            userConfirmationCodes.put(recipientEmail, confirmationCode);
            JOptionPane.showMessageDialog(this, "Confirmation code sent to your email.");
            enableChangePasswordButton();
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send confirmation code. Please try again.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnThoat = new javax.swing.JButton();
        btnQuayVe = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnDoiPass = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(40, 46, 62));

        jPanel2.setBackground(new java.awt.Color(46, 56, 86));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/houselogo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("TRỌ VIỆT");

        btnThoat.setBackground(new java.awt.Color(255, 205, 31));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(40, 46, 62));
        btnThoat.setText("X");
        btnThoat.setPreferredSize(new java.awt.Dimension(38, 39));
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnQuayVe.setBackground(new java.awt.Color(255, 205, 31));
        btnQuayVe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnQuayVe.setForeground(new java.awt.Color(40, 46, 62));
        btnQuayVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/return (1).png"))); // NOI18N
        btnQuayVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayVeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(85, 85, 85)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnQuayVe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuayVe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)))
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("QUÊN MẬT KHẨU");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Email:");

        txtCode.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtCode.setForeground(new java.awt.Color(40, 46, 62));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mật khẩu:");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(40, 46, 62));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mã xác nhận:");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(40, 46, 62));

        btnDoiPass.setBackground(new java.awt.Color(255, 205, 31));
        btnDoiPass.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDoiPass.setForeground(new java.awt.Color(40, 46, 62));
        btnDoiPass.setText("ĐỔI");
        btnDoiPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiPassActionPerformed(evt);
            }
        });

        btnSend.setBackground(new java.awt.Color(255, 205, 31));
        btnSend.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSend.setForeground(new java.awt.Color(40, 46, 62));
        btnSend.setText("GỬI");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCode, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                .addComponent(txtEmail))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(btnSend)
                        .addGap(31, 31, 31)
                        .addComponent(btnDoiPass)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDoiPass, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuayVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayVeActionPerformed
        try {
            // TODO add your handling code here:
            TrangChuChung tc = new TrangChuChung();
            tc.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(QuenMatKhau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnQuayVeActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn đóng ứng dụng?", "Thoát?", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnDoiPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiPassActionPerformed
        // TODO add your handling code here:
        changePassword();
        this.dispose();
        DangNhap dangnhap = new DangNhap();
        dangnhap.setVisible(true);
        
    }//GEN-LAST:event_btnDoiPassActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        sendConfirmationCode();
    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuenMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiPass;
    private javax.swing.JButton btnQuayVe;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
