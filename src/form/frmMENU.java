/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javax.swing.JOptionPane;

public class frmMENU extends javax.swing.JFrame {

    
    public frmMENU() {
        initComponents();
    }

    // <editor-fold desc="Não modifique">
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogo = new javax.swing.JLabel();
        barMenu = new javax.swing.JMenuBar();
        menCadastrar = new javax.swing.JMenu();
        menPaciente = new javax.swing.JMenuItem();
        menUsuario = new javax.swing.JMenuItem();
        barConsulta = new javax.swing.JMenu();
        menMarcarConsulta = new javax.swing.JMenuItem();
        menMarcarRetorno = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Health Clinic");
        setExtendedState(6);
        setLocation(new java.awt.Point(490, 200));
        setName("frm_MENU"); // NOI18N

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/LOGO_TRANSPARENTE.png"))); // NOI18N

        barMenu.setPreferredSize(new java.awt.Dimension(114, 35));

        menCadastrar.setText("Cadastrar");

        menPaciente.setText("Cadastrar Paciente");
        menPaciente.setName("bnt_CAD_PACIENTE"); // NOI18N
        menPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPacienteActionPerformed(evt);
            }
        });
        menCadastrar.add(menPaciente);

        menUsuario.setText("Cadastrar Usuário");
        menUsuario.setName("bnt_CAD_USUARIO"); // NOI18N
        menUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menUsuarioActionPerformed(evt);
            }
        });
        menCadastrar.add(menUsuario);

        barMenu.add(menCadastrar);

        barConsulta.setText("Consulta");

        menMarcarConsulta.setText("Marcar Consulta");
        menMarcarConsulta.setName("bnt_MARCAR_CONSULTA"); // NOI18N
        menMarcarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menMarcarConsultaActionPerformed(evt);
            }
        });
        barConsulta.add(menMarcarConsulta);

        menMarcarRetorno.setText("Marcar Retorno");
        menMarcarRetorno.setName("bnt_MARCAR_RETORNO"); // NOI18N
        menMarcarRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menMarcarRetornoActionPerformed(evt);
            }
        });
        barConsulta.add(menMarcarRetorno);

        barMenu.add(barConsulta);

        setJMenuBar(barMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(154, Short.MAX_VALUE)
                .addComponent(lblLogo)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 445, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // </editor-fold>
    
    private void menPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPacienteActionPerformed
     
        try
        {
            frmCADASTRAR_PACIENTE cad_pac = new frmCADASTRAR_PACIENTE();

            cad_pac.setVisible(true);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_menPacienteActionPerformed

    private void menUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menUsuarioActionPerformed
    
        try
        {
            frmCADASTRAR cad = new frmCADASTRAR();

            cad.setVisible(true);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_menUsuarioActionPerformed

    private void menMarcarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menMarcarConsultaActionPerformed
    
        try
        {
            frmCONSULTA con = new frmCONSULTA();

            con.setVisible(true);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_menMarcarConsultaActionPerformed

    private void menMarcarRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menMarcarRetornoActionPerformed
    
        try
        {
            frmCONSULTA con = new frmCONSULTA();

            con.setTitle("Marcar retorno");
            con.setVisible(true);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_menMarcarRetornoActionPerformed
   
    // <editor-fold desc="Método main">
    
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
            java.util.logging.Logger.getLogger(frmMENU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMENU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMENU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMENU.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMENU().setVisible(true);
            }
        });
    }

    // </editor-fold>
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu barConsulta;
    private javax.swing.JMenuBar barMenu;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JMenu menCadastrar;
    private javax.swing.JMenuItem menMarcarConsulta;
    private javax.swing.JMenuItem menMarcarRetorno;
    private javax.swing.JMenuItem menPaciente;
    private javax.swing.JMenuItem menUsuario;
    // End of variables declaration//GEN-END:variables
}
