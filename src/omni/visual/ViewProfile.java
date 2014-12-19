/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import omni.model.User;
import omni.model.WriteToJSON;

/**
 *
 * @author Grender
 */
public class ViewProfile extends javax.swing.JFrame {

    /**
     * Creates new form ViewProfile
     */
    private Enumeration<String> nameEnum;
    private Enumeration<Integer> timesEnum;
    private Hashtable<String, Integer> accesosCount;

    private static final Color BG_GENERAL_BLUE = new Color(202, 238, 255);
    private static final Color BG_RESOURCE_LIGHT_BLUE = new Color(235, 248, 255);

    private static final ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/res/icon.png"));

    private WriteToJSON wtjson = new WriteToJSON();

    public ViewProfile(final User actualUser) {
        initComponents();

        this.setIconImage(icon.getImage());
        this.setTitle("Omni | Ver perfil");
        this.setLocationRelativeTo(null);

        this.labelFieldNewPass.setVisible(false);
        this.labelRepeatPass.setVisible(false);
        this.pwFieldNewPass.setVisible(false);
        this.pwFieldRepeatNewPass.setVisible(false);
        this.btnChangePass.setVisible(false);
        this.pwFieldActualPass.setVisible(false);
        this.labelActualPass.setVisible(false);
        
        this.jPanel1.setBackground(BG_GENERAL_BLUE);
        this.jPanel2.setBackground(BG_GENERAL_BLUE);
        this.jPanel3.setBackground(BG_GENERAL_BLUE);
        this.textFieldNombre.setBackground(BG_RESOURCE_LIGHT_BLUE);
        this.pwFieldNewPass.setBackground(BG_RESOURCE_LIGHT_BLUE);
        this.pwFieldRepeatNewPass.setBackground(BG_RESOURCE_LIGHT_BLUE);
        this.pwFieldActualPass.setBackground(BG_RESOURCE_LIGHT_BLUE);
        this.jTextArea1.setBackground(BG_RESOURCE_LIGHT_BLUE);
        this.jTextArea1.setLineWrap(true);
        this.jTextArea1.setWrapStyleWord(true);

        accesosCount = actualUser.getAccesosCount();
        nameEnum = accesosCount.keys();
        timesEnum = accesosCount.elements();

        while (nameEnum.hasMoreElements()) {
            this.jTextArea1.setText(this.jTextArea1.getText() + "· " + nameEnum.nextElement()
                    + " ha sido abierto " + timesEnum.nextElement() + " vez / veces.\n");
        }

        this.textFieldNombre.setText(actualUser.getNombre());
        
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportViewer(accesosCount);
            }
            
        });
        
        this.jButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ViewProfile.this.dispose();
            }

        });

        this.checkBoxChangePass.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBoxChangePass.isSelected()) {
                    ViewProfile.this.labelFieldNewPass.setVisible(true);
                    ViewProfile.this.pwFieldNewPass.setVisible(true);
                    ViewProfile.this.pwFieldRepeatNewPass.setVisible(true);
                    ViewProfile.this.btnChangePass.setVisible(true);
                    ViewProfile.this.labelRepeatPass.setVisible(true);
                    ViewProfile.this.labelActualPass.setVisible(true);
                    ViewProfile.this.pwFieldActualPass.setVisible(true);
                } else {
                    ViewProfile.this.labelFieldNewPass.setVisible(false);
                    ViewProfile.this.pwFieldNewPass.setVisible(false);
                    ViewProfile.this.pwFieldRepeatNewPass.setVisible(false);
                    ViewProfile.this.btnChangePass.setVisible(false);
                    ViewProfile.this.labelRepeatPass.setVisible(false);
                    ViewProfile.this.pwFieldActualPass.setVisible(false);
                    ViewProfile.this.labelActualPass.setVisible(false);
                }
            }

        });

        this.btnChangePass.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (new String(ViewProfile.this.pwFieldNewPass.getPassword()).
                        equals(new String(ViewProfile.this.pwFieldRepeatNewPass.getPassword()))) {
                    
                    String passActual = new String(ViewProfile.this.pwFieldActualPass.getPassword());

                    if (passActual.equals(actualUser.getPass())) {
                        actualUser.setPass(new String(ViewProfile.this.pwFieldNewPass.getPassword()));
                        wtjson.updateElement(actualUser, true);
                        ViewProfile.this.pwFieldActualPass.setText("");
                        ViewProfile.this.pwFieldNewPass.setText("");
                        ViewProfile.this.pwFieldRepeatNewPass.setText("");
                        JOptionPane.showMessageDialog(null, "La contraseña se ha cambiado satisfactoriamente.",
                                "Contraseña cambiada", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Debes introducir la contraseña actual",
                                "La contraseña no es la actual", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "La contraseña actual no coincide",
                                "La contraseña no coincide", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
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
        labelNombre = new javax.swing.JLabel();
        textFieldNombre = new javax.swing.JTextField();
        checkBoxChangePass = new javax.swing.JCheckBox();
        pwFieldNewPass = new javax.swing.JPasswordField();
        labelFieldNewPass = new javax.swing.JLabel();
        btnChangePass = new javax.swing.JButton();
        pwFieldRepeatNewPass = new javax.swing.JPasswordField();
        labelRepeatPass = new javax.swing.JLabel();
        pwFieldActualPass = new javax.swing.JPasswordField();
        labelActualPass = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        labelNombre.setText("Nombre: ");

        textFieldNombre.setEditable(false);
        textFieldNombre.setMaximumSize(new java.awt.Dimension(6, 20));

        checkBoxChangePass.setText("Cambiar contraseña");

        labelFieldNewPass.setText("Nueva: ");

        btnChangePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/checkmark-26.png"))); // NOI18N

        pwFieldRepeatNewPass.setToolTipText("Repite la nueva contraseña");

        labelRepeatPass.setText("Repite: ");

        labelActualPass.setText("Actual: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(checkBoxChangePass)
                                .addGap(32, 32, 32)
                                .addComponent(labelFieldNewPass))
                            .addComponent(labelRepeatPass, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(pwFieldNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelActualPass))
                            .addComponent(pwFieldRepeatNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(pwFieldActualPass, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelNombre)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre)
                    .addComponent(textFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkBoxChangePass)
                            .addComponent(pwFieldNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFieldNewPass)
                            .addComponent(pwFieldActualPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelActualPass))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pwFieldRepeatNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelRepeatPass)))
                    .addComponent(btnChangePass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Número de veces que un acceso ha sido abierto"));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton2.setText("Salir");

        jButton1.setText("Exportar informe (PDF)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePass;
    private javax.swing.JCheckBox checkBoxChangePass;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelActualPass;
    private javax.swing.JLabel labelFieldNewPass;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelRepeatPass;
    private javax.swing.JPasswordField pwFieldActualPass;
    private javax.swing.JPasswordField pwFieldNewPass;
    private javax.swing.JPasswordField pwFieldRepeatNewPass;
    private javax.swing.JTextField textFieldNombre;
    // End of variables declaration//GEN-END:variables
}
