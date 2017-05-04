/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RMISecurityManager;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author NgaPC
 */
public class TheScreenServer extends javax.swing.JFrame {

    private JFileChooser fileChooser;
    private ServerInterface server;

    public TheScreenServer() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bt_ChooseFileSyn = new javax.swing.JButton();
        bt_StartSyn = new javax.swing.JButton();
        bt_StopSyn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tar_IPClient = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 287, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setText("   Choose File:");

        bt_ChooseFileSyn.setText("Choose File to Synchronize");
        bt_ChooseFileSyn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ChooseFileSynActionPerformed(evt);
            }
        });

        bt_StartSyn.setText("Start");
        bt_StartSyn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_StartSynActionPerformed(evt);
            }
        });

        bt_StopSyn.setText("Stop");
        bt_StopSyn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_StopSynActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addComponent(bt_StartSyn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(bt_StopSyn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addComponent(bt_ChooseFileSyn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_ChooseFileSyn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_StartSyn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_StopSyn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MyStore Server");

        tar_IPClient.setColumns(20);
        tar_IPClient.setRows(5);
        jScrollPane1.setViewportView(tar_IPClient);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_ChooseFileSynActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ChooseFileSynActionPerformed
        fileChooser = new JFileChooser();
        // Sets the string that goes in the JFileChooser window's title bar.
        fileChooser.setDialogTitle("Choose Synchronization Folder");
        // Set default thư mục hiện tại
        fileChooser.setCurrentDirectory(new java.io.File("C:\\server"));
        // Sets the JFileChooser to allow the user to just select only directories.        
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Determines whether the AcceptAll FileFilter is used as an available choice in the choosable filter list.
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            bt_ChooseFileSyn.setText("" + fileChooser.getSelectedFile());
        } else {
            JOptionPane.showMessageDialog(null, "No selection");
        }

    }//GEN-LAST:event_bt_ChooseFileSynActionPerformed

    private void bt_StopSynActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_StopSynActionPerformed
        bt_StopSyn.addActionListener((ActionEvent e) -> {
            try {
                if (bt_StopSyn.getText().equalsIgnoreCase("Stop")) {
                    server.stop();
                    bt_StartSyn.setEnabled(true);
                    bt_StopSyn.setEnabled(false);
                    bt_StopSyn.setText("Stopped");
                    bt_StartSyn.setText("Start");
                    bt_ChooseFileSyn.setText("Choose File to Synchronize");
                }
            } catch (Exception ex) {
                System.out.println("error: " + ex.getMessage());
            }
        });
    }//GEN-LAST:event_bt_StopSynActionPerformed
//Choose File to Synchronize
    private void bt_StartSynActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_StartSynActionPerformed
        try {
            if (bt_ChooseFileSyn.getText().equals("Choose File to Synchronize")) {
                File defaultFile = new File("C:\\Users\\NgaPC\\Desktop\\server");
                if (!defaultFile.exists()) {
                    // tạo file nếu không có
                    if (defaultFile.mkdir()) {
                        bt_ChooseFileSyn.setText("C:\\server");
                        // thông báo tạo thành công
                        JOptionPane.showMessageDialog(null, "Directory created!");
                    } else {
                        // lý do gì đó khiến nó thất bại
                        JOptionPane.showMessageDialog(null, "Create directory failse");
                    }
                }
                // Nếu thư mục tồn tại, trả về địa chỉ ip của máy chủ RMI
                System.getProperty("java.rmi.server.hostname", getIpServer());
                System.out.println("" + getIpServer());
                bt_ChooseFileSyn.setText(defaultFile.getAbsolutePath());
                // Cài đặt quản lý an ninh mạng
                /*
                    RMISecurityManager implements a policy identical to the policy 
                    implemented by SecurityManager. 
                    RMI applications should use the SecurityManager class 
                    or another appropriate SecurityManager implementation instead of this class. 
                    RMI's class loader will download classes from remote locations only 
                    if a security manager has been set.
                
                 */
                if (System.getSecurityManager() == null) {
                    System.setSecurityManager(new RMISecurityManager());
                }
                server = new ServerImpl(defaultFile);
            } else {
                // Chọn folder
                // Kiểm tra địa chỉ
                System.getProperty("java.rmi.server.hostname", getIpServer());
                if (System.getSecurityManager() == null) {
                    System.setSecurityManager(new RMISecurityManager());
                }
                server = new ServerImpl(fileChooser.getSelectedFile());
            }
            server.start();
            bt_StartSyn.setText("Started");
            bt_StopSyn.setText("Stop");
            bt_StartSyn.setEnabled(false);
            bt_StopSyn.setEnabled(true);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }//GEN-LAST:event_bt_StartSynActionPerformed
    // Hàm xác định địa chỉ IP máy chủ địa phương
    // thật ra chỗ này tớ cũng chưa hiểu lắm
    public static String getIpServer() {
        String ipAddress = null;
        // net trả về danh sách địa chỉ ip trên 1 máy
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        // Kiểm tra tất cả yếu tố của đối tượng net
        while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            // Lấy địa chỉ của từng đối tượng net
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            //Kiểm tra yế tố của đối tượng address
            while (addresses.hasMoreElements()) {
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address) {
                    // Kiểm tra có phải địa chỉ local hay không??? 10/8, 172.16/12 and 192.168/16.
                    if (ip.isSiteLocalAddress()) {
                        // Lấy ip của địa chỉ
                        ipAddress = ip.getHostAddress();
                    }
                }
            }
        }
        return ipAddress;
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TheScreenServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TheScreenServer().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_ChooseFileSyn;
    private javax.swing.JButton bt_StartSyn;
    private javax.swing.JButton bt_StopSyn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea tar_IPClient;
    // End of variables declaration//GEN-END:variables

    public JTextArea getTar_IPClient() {
        return tar_IPClient;
    }

    public void setTar_IPClient(JTextArea tar_IPClient) {
        this.tar_IPClient = tar_IPClient;
    }
    
}
