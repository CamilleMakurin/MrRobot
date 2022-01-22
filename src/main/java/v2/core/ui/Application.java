package v2.core.ui;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import v2.configuration.Configuration;
import v2.core.ApplicationContext;
import v2.core.action.wfconfig.WFConfigRepository;
import v2.core.listener.KeyboardListener;
import v2.core.listener.MouseListener;
import v2.core.workflow.WorkflowService;
import v2.map.generator.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Component
public class Application {

    private final MouseListener mouseListener;
    private final KeyboardListener keyboardListener;
    private final WorkflowService workflowService;
    private final WFConfigRepository wfConfigRepository;
    private final MapGenerator mapGenerator;

    @Autowired
    public Application(WorkflowService workflowService,
                       MouseListener mouseListener,
                       KeyboardListener keyboardListener,
                       WFConfigRepository wfConfigRepository,
                       MapGenerator mapGenerator) {
        this.workflowService = workflowService;
        this.mouseListener = mouseListener;
        this.keyboardListener = keyboardListener;
        this.wfConfigRepository = wfConfigRepository;
        this.mapGenerator = mapGenerator;
    }

    public void run() {
        if (Configuration.runWFRecorder) {
            initWFRecorder();
        }
        if (Configuration.runMapGenerator) {
            mapGenerator.generateMaps();
        }
    }

    private void initWFRecorder() {
        intitPeripheryListeners();
        wfConfigRepository.initConfigurations();
        if (Configuration.runGUI) {
            JFrame frame = new JFrame("My First GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);
            JButton startRecordingButton = new JButton("Record");
            JButton stopRecordingButton = new JButton("Stop");
            JButton executeRecordingButton = new JButton("Execute");

            startRecordingButton.addActionListener(new StartButtonEventListener(workflowService));
            stopRecordingButton.addActionListener(new StopButtonEventListener(workflowService));
            executeRecordingButton.addActionListener(new ExecuteButtonEventListener(workflowService));

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

            frame.setLayout(new GridLayout(2, 1));
            frame.getContentPane().add(panel); // Adds Button to content pane of frame
            frame.getContentPane().add(textPanel);

            frame.setVisible(true);
        }
    }

    private void intitPeripheryListeners() {
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeMouseMotionListener(mouseListener);
        GlobalScreen.addNativeKeyListener(keyboardListener);
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

