/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import classes.Consulta;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author aluno
 */
public class frmCONSULTA extends javax.swing.JFrame {

    /**
     * Creates new form CONSULTA
     */
    public frmCONSULTA() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAgendar = new javax.swing.JButton();
        panDataHora = new javax.swing.JPanel();
        lblData = new javax.swing.JLabel();
        ftfData = new javax.swing.JFormattedTextField();
        lblHorario = new javax.swing.JLabel();
        ftfHorario = new javax.swing.JFormattedTextField();
        panPaciente = new javax.swing.JPanel();
        lblNomePaciente = new javax.swing.JLabel();
        lblCPFPaciente = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        ftfCPFPaciente = new javax.swing.JFormattedTextField();
        panMedico = new javax.swing.JPanel();
        lblCPFMedico = new javax.swing.JLabel();
        txtNomeMedico = new javax.swing.JTextField();
        ftfCPFMedico = new javax.swing.JFormattedTextField();
        lblNomeMedico = new javax.swing.JLabel();
        cmbEspecialidade = new javax.swing.JComboBox<>();
        lblEspecialidade = new javax.swing.JLabel();
        chkRetorno = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Marcar Consulta");
        setLocation(new java.awt.Point(490, 200));

        btnAgendar.setText("Agendar");
        btnAgendar.setName("bnt_AGENDAR"); // NOI18N
        btnAgendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarActionPerformed(evt);
            }
        });

        panDataHora.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblData.setText("Data:");
        lblData.setName("lbl_DATA"); // NOI18N

        try {
            ftfData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblHorario.setText("Horário:");
        lblHorario.setName("lbl_HORARIO"); // NOI18N

        try {
            ftfHorario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout panDataHoraLayout = new javax.swing.GroupLayout(panDataHora);
        panDataHora.setLayout(panDataHoraLayout);
        panDataHoraLayout.setHorizontalGroup(
            panDataHoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDataHoraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblData)
                .addGap(18, 18, 18)
                .addComponent(ftfData, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblHorario)
                .addGap(18, 18, 18)
                .addComponent(ftfHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        panDataHoraLayout.setVerticalGroup(
            panDataHoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDataHoraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panDataHoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblData)
                    .addComponent(ftfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHorario)
                    .addComponent(ftfHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Dados do Paciente"));

        lblNomePaciente.setText("Nome Completo:");
        lblNomePaciente.setName("lbl_NOME_COMPLETO"); // NOI18N

        lblCPFPaciente.setText("CPF:");
        lblCPFPaciente.setName("lbl_CPF"); // NOI18N

        txtNomePaciente.setName("txt_NOME"); // NOI18N

        try {
            ftfCPFPaciente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout panPacienteLayout = new javax.swing.GroupLayout(panPaciente);
        panPaciente.setLayout(panPacienteLayout);
        panPacienteLayout.setHorizontalGroup(
            panPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNomePaciente)
                    .addComponent(lblCPFPaciente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ftfCPFPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(txtNomePaciente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panPacienteLayout.setVerticalGroup(
            panPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomePaciente)
                    .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCPFPaciente)
                    .addComponent(ftfCPFPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panMedico.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Dados do Médico"));

        lblCPFMedico.setText("CPF:");
        lblCPFMedico.setName("lbl_CPF"); // NOI18N

        txtNomeMedico.setName("txt_NOME"); // NOI18N

        try {
            ftfCPFMedico.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblNomeMedico.setText("Nome:");
        lblNomeMedico.setName("lbl_NOME"); // NOI18N

        cmbEspecialidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Clínico Geral", "Dermatologia", "Neurologia", "Oftalmologia", "Ortopedia", "Pediatria" }));
        cmbEspecialidade.setToolTipText("");
        cmbEspecialidade.setName("cmb_CARGO"); // NOI18N

        lblEspecialidade.setText("Especialidade:");
        lblEspecialidade.setName("lbl_ESPECIALIDADE"); // NOI18N

        javax.swing.GroupLayout panMedicoLayout = new javax.swing.GroupLayout(panMedico);
        panMedico.setLayout(panMedicoLayout);
        panMedicoLayout.setHorizontalGroup(
            panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMedicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panMedicoLayout.createSequentialGroup()
                        .addGroup(panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNomeMedico)
                            .addComponent(lblEspecialidade))
                        .addGap(18, 18, 18)
                        .addGroup(panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ftfCPFMedico)
                            .addComponent(cmbEspecialidade, 0, 180, Short.MAX_VALUE)
                            .addComponent(txtNomeMedico)))
                    .addComponent(lblCPFMedico))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        panMedicoLayout.setVerticalGroup(
            panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panMedicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNomeMedico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCPFMedico)
                    .addComponent(ftfCPFMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEspecialidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEspecialidade))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chkRetorno.setText("Retorno");
        chkRetorno.setName("ckb_RETORNO"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(chkRetorno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgendar))
                            .addComponent(panMedico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panDataHora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgendar)
                    .addComponent(chkRetorno))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarActionPerformed
        
        try
        {
            Consulta con = new Consulta(txtNomePaciente.getText(), ftfCPFPaciente.getText(), 
                    txtNomePaciente.getText(), ftfCPFMedico.getText(), 
                    cmbEspecialidade.getSelectedItem().toString(), ftfData.getText(), 
                    ftfHorario.getText(), String.valueOf(chkRetorno.isSelected()));
            
            JOptionPane.showMessageDialog(null, con.insere(txtNomePaciente.getText(), 
                    ftfCPFPaciente.getText(), txtNomePaciente.getText(), ftfCPFMedico.getText(), 
                    cmbEspecialidade.getSelectedItem().toString(), ftfData.getText(), 
                    ftfHorario.getText(), String.valueOf(chkRetorno.isSelected())));

            gerarPDF();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnAgendarActionPerformed

    private void gerarPDF()
    {
        com.itextpdf.text.Document doc = null;
        OutputStream os = null;
        
        try
        {
            doc = new com.itextpdf.text.Document(PageSize.A4, 72, 72, 72, 72);
            
            os = new FileOutputStream("pdf.pdf");
            
            PdfWriter.getInstance(doc, os);
            
            doc.open();
            
            doc.addTitle("Ficha de Consulta");
            
            Paragraph p = new Paragraph("Dados do Paciente:");
            Paragraph p1 = new Paragraph(lblNomePaciente.getText() + " " + txtNomePaciente.getText());
            Paragraph p2 = new Paragraph(lblCPFPaciente.getText() + " " + ftfCPFPaciente.getText());
            
            Paragraph p3 = new Paragraph("Dados do Médico:");
            Paragraph p4 = new Paragraph(lblNomeMedico.getText() + " " + txtNomeMedico.getText());
            Paragraph p5 = new Paragraph(lblCPFMedico.getText() + " " + ftfCPFMedico.getText());
            Paragraph p6 = new Paragraph(lblEspecialidade.getText() + " " + 
                    cmbEspecialidade.getSelectedItem().toString());
            
            Paragraph p7 = new Paragraph(lblData.getText() + " " + ftfData.getText());
            Paragraph p8 = new Paragraph(lblHorario.getText() + " " + ftfHorario.getText());
            
            /*com.itextpdf.text.Image img = 
                    com.itextpdf.text.Image(getClass().getResource("\\imagens\\LOGO_TRANSPARENTE.png"));
            
            img.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            
            doc.add(img);*/
            
            doc.add(p);
            doc.add(p1);
            doc.add(p2);
            doc.add(p3);
            doc.add(p4);
            doc.add(p5);
            doc.add(p6);
            doc.add(p7);
            doc.add(p8);            
        }
        catch(DocumentException | IOException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        finally
        {
            if(doc != null)
            {
                doc.close();
            }
            
            if(os != null)
            {
                try
                {
                    os.close();
                }
                catch(IOException ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }      
    }
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
            java.util.logging.Logger.getLogger(frmCONSULTA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCONSULTA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCONSULTA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCONSULTA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCONSULTA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgendar;
    private javax.swing.JCheckBox chkRetorno;
    private javax.swing.JComboBox<String> cmbEspecialidade;
    private javax.swing.JFormattedTextField ftfCPFMedico;
    private javax.swing.JFormattedTextField ftfCPFPaciente;
    private javax.swing.JFormattedTextField ftfData;
    private javax.swing.JFormattedTextField ftfHorario;
    private javax.swing.JLabel lblCPFMedico;
    private javax.swing.JLabel lblCPFPaciente;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblEspecialidade;
    private javax.swing.JLabel lblHorario;
    private javax.swing.JLabel lblNomeMedico;
    private javax.swing.JLabel lblNomePaciente;
    private javax.swing.JPanel panDataHora;
    private javax.swing.JPanel panMedico;
    private javax.swing.JPanel panPaciente;
    private javax.swing.JTextField txtNomeMedico;
    private javax.swing.JTextField txtNomePaciente;
    // End of variables declaration//GEN-END:variables
}