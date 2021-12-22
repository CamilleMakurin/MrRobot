package v2.ui;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import v2.ApplicationContext;
import v2.listener.KeyboardListener;
import v2.listener.MouseListener;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ApplicationGUI {

    public void run(boolean enableGUI) {
        intitPeripheryListeners();
        if (enableGUI){
            JFrame frame = new JFrame("My First GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);
            JButton startRecordingButton = new JButton("Record");
            JButton stopRecordingButton = new JButton("Stop");
            JButton executeRecordingButton = new JButton("Execute");

            startRecordingButton.addActionListener(new StartButtonEventListener());
            stopRecordingButton.addActionListener(new StopButtonEventListener());
            executeRecordingButton.addActionListener(new ExecuteButtonEventListener());

            JPanel panel = new JPanel();
            panel.add(startRecordingButton);
            panel.add(stopRecordingButton);
            panel.add(executeRecordingButton);
            panel.setLayout(new FlowLayout(1, 1, 1));

            JPanel textPanel = new JPanel();
            JTextField outputFileNameInput = new JTextField();
            outputFileNameInput.setColumns(6);
            textPanel.add(outputFileNameInput);
            ApplicationContext.getContext().setTextField(outputFileNameInput);

            frame.setLayout(new GridLayout(2,1));
            frame.getContentPane().add(panel); // Adds Button to content pane of frame
            frame.getContentPane().add(textPanel);

            frame.setVisible(true);
        }

    }

    private void intitPeripheryListeners() {
        MouseListener globalMouseListener = new MouseListener();
        GlobalScreen.addNativeMouseListener(globalMouseListener);
        GlobalScreen.addNativeMouseMotionListener(globalMouseListener);
        GlobalScreen.addNativeKeyListener(new KeyboardListener());
        registerNativeHook();
    }

    private void registerNativeHook() {
        try {
            GlobalScreen.registerNativeHook();
            disableLogging();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook:");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    private void disableLogging() {
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }
}

