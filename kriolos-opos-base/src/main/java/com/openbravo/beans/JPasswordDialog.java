//    KrOS POS  - Open Source Point Of Sale
//    Copyright (c) 2009-2018 uniCenta & previous Openbravo POS works
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.beans;

import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorPassword;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.Hashcypher;
import java.awt.*;
import javax.swing.*;

public class JPasswordDialog extends javax.swing.JDialog {
    
    private static LocaleResources m_resources;
    private static final long serialVersionUID = 1L;
    private JEditorKeys m_jKeys = new JEditorKeys();
    private JEditorPassword m_jpassword = new JEditorPassword();

    private String m_sPassword;
    
    /** Creates new form JPasswordDialog
     * @param parent
     * @param modal */
    public JPasswordDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal); 
        initComponents();
        init();
    }
    /** Creates new form JPasswordDialog
     * @param parent
     * @param modal */
    public JPasswordDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);   
        initComponents();
        init();
    }    
    
    private void init() {
     
        jPanelKe.add(m_jKeys);
        jPanelInput.add(m_jpassword);
        
        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("beans_messages");
        }
       
        getRootPane().setDefaultButton(jcmdOK);   
        
        m_jpassword.addEditorKeys(m_jKeys);
        m_jpassword.reset();
        m_jpassword.activate();
        
        m_jPanelTitle.setBorder(RoundedBorder.createGradientBorder());

        m_sPassword = null;
    }
    
    private void setTitle(String title, String message, Icon icon) {
        setTitle(title);
        m_lblMessage.setText(message);
        m_lblMessage.setIcon(icon);
    }
    
    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        } else if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window) parent;
        } else {
            return getWindow(parent.getParent());
        }
    }

    /**
     *
     * @param parent
     * @param title
     * @return
     */
    public static String showEditPassword(Component parent, String title) {
        return showEditPassword(parent, title, null, null);
    }

    /**
     *
     * @param parent
     * @param title
     * @param message
     * @return
     */
    public static String showEditPassword(Component parent, String title, String message) {
        return showEditPassword(parent, title, message, null);
    }

    /**
     *
     * @param parent
     * @param title
     * @param message
     * @param icon
     * @return
     */
    public static String showEditPassword(Component parent, String title, String message, Icon icon) {
        
        Window window = getWindow(parent);      
        
        JPasswordDialog myMsg;
        if (window instanceof Frame) { 
            myMsg = new JPasswordDialog((Frame) window, true);
        } else {
            myMsg = new JPasswordDialog((Dialog) window, true);
        }
        
        myMsg.setTitle(title, message, icon);
        myMsg.setVisible(true);
        return myMsg.m_sPassword;
    }
    
    /**
     *
     * @param parent
     * @return
     */
    public static String changePassword(Component parent) {
        // Show the changePassword dialogs but do not check the old password
        
        String sPassword = JPasswordDialog.showEditPassword(parent,                 
                AppLocal.getIntString("label.Password"), 
                AppLocal.getIntString("label.passwordnew"),
                new ImageIcon(Hashcypher.class.getResource("/com/openbravo/images/password.png")));
        if (sPassword != null) {
            String sPassword2 = JPasswordDialog.showEditPassword(parent,                 
                    AppLocal.getIntString("label.Password"), 
                    AppLocal.getIntString("label.passwordrepeat"),
                    new ImageIcon(Hashcypher.class.getResource("/com/openbravo/images/password.png")));
            if (sPassword2 != null) {
                if (sPassword.equals(sPassword2)) {
                    return  Hashcypher.hashString(sPassword);
                } else {
                    JOptionPane.showMessageDialog(parent, AppLocal.getIntString("message.changepassworddistinct"), AppLocal.getIntString("message.title"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }   
        
        return null;
    }

    /**
     *
     * @param parent
     * @param sOldPassword
     * @return
     */
    public static String changePassword(Component parent, String sOldPassword) {
        
        String sPassword = JPasswordDialog.showEditPassword(parent,                 
                AppLocal.getIntString("label.Password"), 
                AppLocal.getIntString("label.passwordold"),
                new ImageIcon(Hashcypher.class.getResource("/com/openbravo/images/password.png")));
        if (sPassword != null) {
            if (Hashcypher.authenticate(sPassword, sOldPassword)) {
                return changePassword(parent);               
            } else {
                JOptionPane.showMessageDialog(parent, AppLocal.getIntString("message.BadPassword"), AppLocal.getIntString("message.title"), JOptionPane.WARNING_MESSAGE);
           }
        }
        return null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jcmdCancel = new javax.swing.JButton();
        jcmdOK = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanelGrid = new javax.swing.JPanel();
        jPanelKe = new javax.swing.JPanel();
        jPanelInput = new javax.swing.JPanel();
        m_jPanelTitle = new javax.swing.JPanel();
        m_lblMessage = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(350, 450));
        setPreferredSize(new java.awt.Dimension(350, 450));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeWindow(evt);
            }
        });

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jcmdCancel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcmdCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/cancel.png"))); // NOI18N
        jcmdCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdCancel.setPreferredSize(new java.awt.Dimension(80, 45));
        jcmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdCancelActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdCancel);

        jcmdOK.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcmdOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/ok.png"))); // NOI18N
        jcmdOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdOK.setPreferredSize(new java.awt.Dimension(80, 45));
        jcmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdOKActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdOK);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setPreferredSize(new java.awt.Dimension(320, 390));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanelGrid.setPreferredSize(new java.awt.Dimension(310, 380));

        jPanelKe.setPreferredSize(new java.awt.Dimension(300, 300));
        jPanelKe.setLayout(new javax.swing.BoxLayout(jPanelKe, javax.swing.BoxLayout.Y_AXIS));
        jPanelGrid.add(jPanelKe);

        jPanelInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanelInput.setPreferredSize(new java.awt.Dimension(300, 50));
        jPanelInput.setLayout(new java.awt.BorderLayout());
        jPanelGrid.add(jPanelInput);

        jPanel2.add(jPanelGrid, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        m_jPanelTitle.setLayout(new java.awt.BorderLayout());

        m_lblMessage.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.darkGray), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        m_jPanelTitle.add(m_lblMessage, java.awt.BorderLayout.CENTER);

        getContentPane().add(m_jPanelTitle, java.awt.BorderLayout.NORTH);

        setSize(new java.awt.Dimension(308, 489));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jNumberKeys21KeyPerformed(com.openbravo.beans.JNumberEvent evt) {//GEN-FIRST:event_jNumberKeys21KeyPerformed
 
    }//GEN-LAST:event_jNumberKeys21KeyPerformed

    private void jcmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdOKActionPerformed
          
        m_sPassword = m_jpassword.getPassword(); 
        setVisible(false);
        dispose();     
        
    }//GEN-LAST:event_jcmdOKActionPerformed

    private void jcmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdCancelActionPerformed

        setVisible(false);
        dispose();    
        
    }//GEN-LAST:event_jcmdCancelActionPerformed

    private void closeWindow(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeWindow

        setVisible(false);
        dispose();
        
    }//GEN-LAST:event_closeWindow

    private void m_jKeysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jKeysActionPerformed
    }//GEN-LAST:event_m_jKeysActionPerformed
       
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelGrid;
    private javax.swing.JPanel jPanelInput;
    private javax.swing.JPanel jPanelKe;
    private javax.swing.JButton jcmdCancel;
    private javax.swing.JButton jcmdOK;
    private javax.swing.JPanel m_jPanelTitle;
    private javax.swing.JLabel m_lblMessage;
    // End of variables declaration//GEN-END:variables
    
}
