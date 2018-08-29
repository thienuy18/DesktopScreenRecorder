/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopscreenrecorder;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcodec.api.awt.AWTSequenceEncoder;

/**
 *
 * @author enyason
 */
public class MainScreenRecorderFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainScreenRecorderFrame
     */
    public MainScreenRecorderFrame() {
        initComponents();

//        initObjects();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonStartRecording = new javax.swing.JButton();
        buttonStopRecording = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        recordStateLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        recordTimeLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelTimer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonStartRecording.setText("Start Recording");
        buttonStartRecording.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStartRecordingActionPerformed(evt);
            }
        });

        buttonStopRecording.setText("Stop Recording");
        buttonStopRecording.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopRecordingActionPerformed(evt);
            }
        });

        jCheckBox1.setText("5s Delay");

        jLabel2.setText("Recording State : ");

        recordStateLabel.setText("Recorder Not Started");

        jLabel4.setText("Record Time : ");

        recordTimeLabel.setText("0 min");

        jLabel6.setText("Timer");

        labelTimer.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonStopRecording)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonStartRecording)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                        .addComponent(jCheckBox1)
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recordStateLabel)
                            .addComponent(recordTimeLabel)
                            .addComponent(labelTimer))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonStartRecording)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonStopRecording)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(recordStateLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(recordTimeLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelTimer))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonStopRecordingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopRecordingActionPerformed

       


        if (isRecording) {
            
             RecordTimer.stop(); 

                    
            recordStateLabel.setText("Recording Stopped");
            recordTimeLabel.setText("" + RecordTimer.getTimeInSec());

            //cancle the timer for time counting here
            timerCount.cancel();
            timerCount.purge();

            // stop the timer from executing the task here
            timerRecord.cancel();
            timerRecord.purge();

            recorderTask.cancel();
            countTask.cancel();

            try {

                encoder.finish();// finish  encoding
                System.out.println("encoding finish " + (RecordTimer.getTimeInSec()) + "s");
                recordStateLabel.setText("recorder Stopped...");
                recordTimeLabel.setText(""+RecordTimer.getTimeInMin()+"min");

            } catch (IOException ex) {
                Logger.getLogger(MainScreenRecorderFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
         isRecording = false;


    }//GEN-LAST:event_buttonStopRecordingActionPerformed

    private void buttonStartRecordingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStartRecordingActionPerformed
        // TODO add your handling code here:

        initObjects("video_ouput");
        isRecording = true;

        int delay = 1000 / 24;

        RecordTimer.reset();

        timerRecord = new Timer("Thread TimerRecord");

        timerCount = new Timer("Thread TimerCount");

        recorderTask = new ScreenRecorderTask(encoder, rectangle);
        countTask = new TimerCountTask(labelTimer);

        timerRecord.scheduleAtFixedRate(recorderTask, 0, delay);
        timerCount.scheduleAtFixedRate(countTask, 0, 1000);

        recordStateLabel.setText("recorder Started...");


    }//GEN-LAST:event_buttonStartRecordingActionPerformed

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
            java.util.logging.Logger.getLogger(MainScreenRecorderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreenRecorderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreenRecorderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreenRecorderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreenRecorderFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonStartRecording;
    private javax.swing.JButton buttonStopRecording;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelTimer;
    private javax.swing.JLabel recordStateLabel;
    private javax.swing.JLabel recordTimeLabel;
    // End of variables declaration//GEN-END:variables

    Rectangle rectangle;
    AWTSequenceEncoder encoder;
    ScreenRecorderTask recorderTask;
    TimerCountTask countTask;

    public static Timer timerRecord;
    Timer timerCount;

    boolean isRecording = false;
    File f;

    private void initObjects(String fileName) {

        rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

//       //create a new file
        f = new File(fileName+".mp4");

        try {
            // initialize the encoder
            encoder = AWTSequenceEncoder.createSequenceEncoder(f, 24 / 4);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenRecorderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
