/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.joda.time.DateTimeUtils;

public class TheFirstScreenClient extends javax.swing.JFrame {

    private JFileChooser fileChooser;
    private ServerInterface server;
    private ClientInterface client;
    private boolean close;
    

    public TheFirstScreenClient() {
        close = false;

        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        tf_IPServer = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bt_ChooseFileSyn = new javax.swing.JButton();
        bt_Connect = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_Close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tf_IPServer.setText("localhost");
        tf_IPServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_IPServerActionPerformed(evt);
            }
        });

        jLabel1.setText("IP Server:");

        jLabel2.setText("File Synchronization:");

        bt_ChooseFileSyn.setText("Choose File to Synchronize");
        bt_ChooseFileSyn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ChooseFileSynActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_IPServer)
                    .addComponent(bt_ChooseFileSyn, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tf_IPServer, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_ChooseFileSyn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        bt_Connect.setText("Connect");
        bt_Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ConnectActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MyStore Client");

        btn_Close.setText("Close");
        btn_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bt_Connect, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btn_Close, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Connect, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Close, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_ChooseFileSynActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ChooseFileSynActionPerformed
        // TODO add your handling code here:
        fileChooser = new JFileChooser();
        // Sets the string that goes in the JFileChooser window's title bar.
        fileChooser.setDialogTitle("Choose Synchronization Folder");
        // Set default thư mục hiện tại
        fileChooser.setCurrentDirectory(new java.io.File("C:\\client"));
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

    // Hàm xác định địa chỉ IP máy chủ địa phương
    public static String getIp() {
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

    private boolean connect() throws Exception {
        System.getProperty("java.rmi.server.hostname", getIp());
        String url = "rmi://" + "localhost" + ":" + "3000"
                + "/" + "server";
        // sử dụng url để kết nối tới server
        server = (ServerInterface) Naming.lookup(url);
        System.out.println("Connected");
        client = new ClientImpl(InetAddress.getLocalHost());

        if (!server.connect(client)) {
            JOptionPane.showMessageDialog(null, "Cannot connect");
            return false;
        } 
//        else if (!server.canWork(client)) { // be pushed to queue but must wait
//            JOptionPane.showMessageDialog(null, "You're pushed to queue. Please wait...");
//            this.bt_Connect.setText("Waiting...");
//            this.bt_Connect.setEnabled(false);
//            (new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (!server.canWork(client) && !close) {}
//                    } catch (RemoteException ex) {
//                        Logger.getLogger(TheFirstScreenClient.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    open();
//                }
//            })).start();
//            return false;
//        }
        System.out.println("IP client" + InetAddress.getLocalHost());
        clockSync();
        return true;
    }

    private static void clockSync() throws Exception {
        String serverIP = tf_IPServer.getText();
        try (
                // Send request
                DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(serverIP);
            byte[] buf = new NtpMessage().toByteArray();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address,
                    123);

            // Set the transmit timestamp *just* before sending the packet
            // ToDo: Does this actually improve performance or not?
            NtpMessage.encodeTimestamp(packet.getData(), 40,
                    (System.currentTimeMillis() / 1000.0) + 2208988800.0);

            socket.send(packet);

            // Get response
            System.out.println("NTP request sent, waiting for response...\n");
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            // Immediately record the incoming timestamp
            double destinationTimestamp = (System.currentTimeMillis() / 1000.0) + 2208988800.0;

            // Process response
            NtpMessage msg = new NtpMessage(packet.getData());

            // Corrected, according to RFC2030 errata
            double roundTripDelay = (destinationTimestamp - msg.originateTimestamp)
                    - (msg.transmitTimestamp - msg.receiveTimestamp);

            double localClockOffset = ((msg.receiveTimestamp - msg.originateTimestamp) + (msg.transmitTimestamp - destinationTimestamp)) / 2;

            // Display response
            System.out.println("NTP server: " + serverIP);
            System.out.println(msg.toString());

            System.out.println("Dest. timestamp:     "
                    + NtpMessage.timestampToString(destinationTimestamp));

            System.out.println("Round-trip delay: "
                    + new DecimalFormat("0.00").format(roundTripDelay * 1000)
                    + " ms");

            System.out.println("Local clock offset: "
                    + new DecimalFormat("0.00").format(localClockOffset * 1000)
                    + " ms");
            System.out.println("Current time " + DateTimeUtils.currentTimeMillis());
            DateTimeUtils.setCurrentMillisOffset((long) (localClockOffset * 1000));
            System.out.println("Current time " + DateTimeUtils.currentTimeMillis());
        }
    }

    private void open() {
        String fileClientPath = fileChooser.getSelectedFile().getAbsolutePath();
        //String fileClientPath = "C:\\Users\\NgaPC\\Desktop\\client";
        TheMainScreenClient t = new TheMainScreenClient(fileClientPath, client, server);
        t.setVisible(true);

        this.setVisible(false);
    }
    
    private void bt_ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ConnectActionPerformed
        try {
            if (tf_IPServer.getText().equals("")) {
                JOptionPane.showMessageDialog(bt_Connect, "bạn chưa nhập địa chỉ server", "lỗi kết nối", JOptionPane.ERROR_MESSAGE);
            } else if (connect()) {
                open();
            }
        } catch (Exception e) {
            System.out.println("Cannot start!");
            e.printStackTrace();
        }
    }//GEN-LAST:event_bt_ConnectActionPerformed

    private void tf_IPServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_IPServerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_IPServerActionPerformed

    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        this.close = true;
        try {
            this.server.disConnect(this.client);
        } catch (RemoteException ex) {
            Logger.getLogger(TheFirstScreenClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_btn_CloseActionPerformed

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
            java.util.logging.Logger.getLogger(TheFirstScreenClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TheFirstScreenClient().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_ChooseFileSyn;
    private javax.swing.JButton bt_Connect;
    private javax.swing.JButton btn_Close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private static javax.swing.JTextField tf_IPServer;
    // End of variables declaration//GEN-END:variables
}
