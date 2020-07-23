package view;

import form.*;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import utils.FrameUtils;

/**
 * 
 * @author William
 */
public class Login extends javax.swing.JFrame {
    
    public Login() {
        initComponents();
        setComponentsProperties ();
        bindComponentsEvents ();
    }
    
    private void setComponentsProperties () {
        try
        {
            Font labelFont = new Font("Monospaced", Font.BOLD, 16);
            Font inputFont = new Font("Monospaced", Font.PLAIN, 16);

            JLabel[] labels = {
                labelSocialSecurityNumber, labelPassword, labelSignUp
            };

            JComponent[] inputs = {
                inputSocialSecurityNumber, inputPassword
            };

            String[] labelsTexts = {
                "SSN:", "Password:", "Sign Up"
            };

            int index = 0;
            for (JLabel label : labels) {
                label.setText(labelsTexts[index]);
                label.setFont(labelFont);
                index++;
            }

            for (JComponent component : inputs) {
                component.setFont(inputFont);
            }

            buttonLogin.setEnabled (false);
            buttonLogin.setFont(labelFont);
            buttonLogin.setText ("Login");

            JFormattedTextField.AbstractFormatter ssnFormatter = new MaskFormatter("###-##-####");
            inputSocialSecurityNumber.setFormatterFactory(new DefaultFormatterFactory(ssnFormatter));
        }
        catch (Exception ex)
        {
            System.out.println (ex.getMessage ());
            JOptionPane.showMessageDialog (this, ex.getMessage ());
        }
    }
    
    private void bindComponentsEvents () {
        labelSignUp.addMouseListener (new MouseAdapter ()
        {
            @Override
            public void mouseClicked (MouseEvent ev)
            {
                if (!FrameUtils.isFrameAlreadyOpen (CreateUpdateEmployee.class)) {
                    CreateUpdateEmployee employeeFrame = new CreateUpdateEmployee ();
                    employeeFrame.setIsUpdate (false);
                    employeeFrame.setVisible (true);
                }
            }
        });
        
        inputSocialSecurityNumber.addKeyListener (new KeyAdapter ()
        {
            @Override
            public void keyTyped (KeyEvent ev)
            {
                checkInputsToUnlockButton ();
            }
        });
        
        inputPassword.addKeyListener (new KeyAdapter ()
        {
            @Override
            public void keyTyped (KeyEvent ev)
            {
                boolean isMaxLength = (inputPassword.getPassword ().length == 20);
                if (isMaxLength) {
                    ev.consume ();
                }
                
                checkInputsToUnlockButton ();
            }
        });
        
        buttonLogin.addActionListener (ev -> {
            // CALLS MENU
        });
    }
    
    private void checkInputsToUnlockButton () {
        String socialSecurityNumber = inputSocialSecurityNumber.getText ();
        char[] password = inputPassword.getPassword ();
        boolean enabled = (socialSecurityNumber.trim ().length () == 11 && password.length != 0);
        buttonLogin.setEnabled (enabled);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        labelPassword = new javax.swing.JLabel();
        labelLock = new javax.swing.JLabel();
        labelSignUp = new javax.swing.JLabel();
        buttonLogin = new javax.swing.JButton();
        inputPassword = new javax.swing.JPasswordField();
        labelSocialSecurityNumber = new javax.swing.JLabel();
        inputSocialSecurityNumber = new javax.swing.JFormattedTextField();
        separator = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Health Clinic");
        setIconImages(getIconImages());
        setLocation(new java.awt.Point(490, 290));
        setName("frm_LOGIN"); // NOI18N
        setResizable(false);

        labelPassword.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelPassword.setText("Password:");
        labelPassword.setName("lbl_SENHA"); // NOI18N

        labelLock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/lock-icon1.png"))); // NOI18N
        labelLock.setText("lbl_img_CADEADO");

        labelSignUp.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelSignUp.setForeground(new java.awt.Color(0, 102, 153));
        labelSignUp.setText("Sign Up");
        labelSignUp.setName("lbl_CADASTRAR"); // NOI18N

        buttonLogin.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        buttonLogin.setText("Login");
        buttonLogin.setName("bnt_ENTRAR"); // NOI18N

        inputPassword.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        labelSocialSecurityNumber.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelSocialSecurityNumber.setText("SSN:");
        labelSocialSecurityNumber.setName("lbl_ID"); // NOI18N

        inputSocialSecurityNumber.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        separator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPassword)
                            .addComponent(labelSocialSecurityNumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputSocialSecurityNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(buttonLogin)
                                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(labelSignUp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(labelLock, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSocialSecurityNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputSocialSecurityNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPassword)
                    .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLogin)
                    .addComponent(labelSignUp))
                .addContainerGap(44, Short.MAX_VALUE))
            .addComponent(labelLock, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(separator)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLogin;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JFormattedTextField inputSocialSecurityNumber;
    private javax.swing.JLabel labelLock;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelSignUp;
    private javax.swing.JLabel labelSocialSecurityNumber;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables
}
