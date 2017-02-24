/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chats;

// data stream

// buffered reader package, read data per line
import static chats.Client.f;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
// data out stream package, socket data output is the same
import java.io.FileOutputStream;
// Exception
import java.io.IOException;
// input stream package and reader
import java.io.InputStream;
import java.io.InputStreamReader;
// print stream package, print a line 
import java.io.PrintStream;
// socket and serversocket
import java.net.ServerSocket;
import java.net.Socket;
// exception logger
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
// file chooser;



// Client class from Frame
public class Server extends javax.swing.JFrame {

    // Client socket 
    static Socket s;
    // Server socket 
    static ServerSocket ss;
    
    static DataOutputStream out;
    // Data from input
    
    /**
     * Creates new form Client
     */
    public Server() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_text = new javax.swing.JTextField();
        msg_send = new javax.swing.JButton();
        start_btn = new javax.swing.JButton();
        stop_btn = new javax.swing.JButton();
        file_btn = new javax.swing.JButton();
        fileAddress_lable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Server's frame");

        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msg_text.setToolTipText("");

        msg_send.setText("Send");
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        start_btn.setText("Start");
        start_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_btnActionPerformed(evt);
            }
        });

        stop_btn.setText("Stop");
        stop_btn.setEnabled(false);
        stop_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop_btnActionPerformed(evt);
            }
        });

        file_btn.setText("Choose");
        file_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                file_btnActionPerformed(evt);
            }
        });

        fileAddress_lable.setText("File address..");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msg_text)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msg_send))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(file_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileAddress_lable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(start_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stop_btn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(start_btn)
                    .addComponent(stop_btn)
                    .addComponent(file_btn)
                    .addComponent(fileAddress_lable))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msg_send))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed
        try {
            out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Send file or message?
        if (fileAddress_lable.getText() != "File address..") {
            // Show info to user;
            msg_area.append("\nStart transfer...\n");
            msg_area.append("File is " + (int) f.length() + " byte.\n");
            try {

                InputStream is = new FileInputStream(f);
                // Get the size of the file
                int length = (int) f.length();
                out.writeChar('f');
                out.writeLong(length);
                byte[] bytes = new byte[32];
                int a;
                while ((a = is.read(bytes, 0, bytes.length)) > 0) {

                    out.write(bytes);
                    length -= a;
                    System.out.println("length:" + length);
                    System.out.println("bytes:" + a);
                    // send a lock of data even if it is not big enough
                    out.flush();
                }
                is.close();
                //show info
                msg_area.append("Transfer finished.\n\n");
                // reset file address
                fileAddress_lable.setText("File address..");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.writeChar('m');
                String msg = msg_text.getText();
                msg_text.setText("");
                msg_area.append("\nMe:" + msg);
                out.writeUTF(msg);
                System.out.println(msg);
                out.flush();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_msg_sendActionPerformed
    public class Receive implements Runnable {
        // Variables
        InputStream is;
        DataInputStream in;
        // Constructor. initislise basic variables
        public Receive() {
            try {
                // Input stream
                is = s.getInputStream();
                in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (in.available() > 0){
                        char type = 0;
                        try {
                            type = in.readChar();
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println(type == 'f');
                        // if file
                        if (type == 'f') {
                            byte[] bytes = new byte[16*1024];
                            FileOutputStream fos;
                            try {
                                fos = new FileOutputStream("D:\\test.jpg");
                                int a = 0;
                                long length = in.readLong();
                                System.out.println(length);
                                msg_area.append("\n\nA client is sending you a file\n size:" + length);
                                while (length > 0) {
                                    a = in.read(bytes, 0, bytes.length);
                                    length -= a;
                                    fos.write(bytes, 0, a);
                                    System.out.println("bytes:" + a);
                                    System.out.println("length:" + length);
                                }
                                msg_area.append("\nFinish receving.\n");
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        } else {
                            try {
                                String msg;
                                msg = in.readUTF();
                                System.out.println(msg);
                                msg_area.append("\nClient:" + msg);
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    // Serversocket listening
    public class Listening implements Runnable { 
        
        @Override
        public void run() {
            try {
                // create serversocket 
                ss = new ServerSocket( 3000); // server starts at 3000 port number;
                System.out.println("Listenning...");
                // show info
                msg_area.append("Server start listening...\n");
                // change btns condition
                start_btn.setEnabled(false);
                stop_btn.setEnabled(true);
                // listening
                s = ss.accept();
                // Start a new thread to receive;
                Thread starter = new Thread(new Receive());
                starter.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    private void start_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_btnActionPerformed
        // Start a new thread to create sever listening
        Thread starter = new Thread(new Listening());
        starter.start();
    }//GEN-LAST:event_start_btnActionPerformed

    private void stop_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stop_btnActionPerformed
        // stop server;
        try {
            // show info
            msg_area.append("Stop server.\n");
            // close serversocket
            ss.close();
        } catch(Exception ex) {
            // show error
            msg_area.append("Failed to stop server.\n");
        }
        // change btn conditions
        start_btn.setEnabled(true);
        stop_btn.setEnabled(false);
    }//GEN-LAST:event_stop_btnActionPerformed

    private void file_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_file_btnActionPerformed
        // file chooser
        JFileChooser chooser = new JFileChooser();

        // show dialog
        int returnVal = chooser.showOpenDialog(null);
        // if choose a file
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            // get file
            f  = chooser.getSelectedFile();
            // get file name
            String filename = f.getName();
            // show file name to user
            // use as a tag to distinguish wheather user sending is  a file or or a msg
            fileAddress_lable.setText("You chose a file: " + filename);
        }
    }//GEN-LAST:event_file_btnActionPerformed

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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileAddress_lable;
    private javax.swing.JButton file_btn;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_send;
    private javax.swing.JTextField msg_text;
    private javax.swing.JButton start_btn;
    private javax.swing.JButton stop_btn;
    // End of variables declaration//GEN-END:variables
}