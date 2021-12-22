package v1.workflowRecorder.controller;

import v1.tmp.ItemLoader;
import v2.action.ActionService;
import v1.workflowRecorder.action.nativeaction.Action;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import v1.workflowRecorder.constant.Configuration;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import v1.workflowRecorder.listener.KeyboardListener;
import v1.workflowRecorder.listener.MouseListener;
import v1.workflowRecorder.player.RecordAndPlay;
import v1.workflowRecorder.player.WorkflowExecutor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    Button pressRecord = new Button();
    @FXML
    Button pressStop = new Button();
    @FXML
    Button pressPlay = new Button();

    @FXML
    Button pressPlayALL = new Button();

    @FXML
    Button pressSave = new Button();
    @FXML
    Button pressOpen = new Button();
    @FXML
    TextField saveFileName = new TextField();
    @FXML
    TextField openFileName = new TextField();

    public static List<Action> actions = new ArrayList<>();
    public ActionService actionService = new ActionService();


    public void startRecord(ActionEvent actionEvent) {
        actions = new ArrayList<>();
        KeyboardListener.resetEvents();
        MouseListener.resetEvents();
        updateButtons(true);
        RecordAndPlay.recordMovement();
    }

    public void stopRecord(ActionEvent actionEvent) {
        RecordAndPlay.stopMovement();
        updateButtons(false);
        boolean recordMouseMoves = true;
        //actions = actionService.createActionsFromNativeEvents(KeyboardListener.events, MouseListener.events, recordMouseMoves);
        if (!actions.isEmpty()) {
            startSave(null);
        }
    }

    public void startPlay(ActionEvent actionEvent) {
        WorkflowExecutor.executeWorkflow();
    }

    public void startPlayAll(ActionEvent actionEvent) {

        try {
            ItemLoader.doWorkflow();
        } catch (IOException e) {
          //  Log.info("Failed to execute: ");
            e.printStackTrace();
        }
    }

    public void startSave(ActionEvent actionEvent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<String> lines = new ArrayList<>();
            lines.addAll(actions.stream().map(action -> {
                try {
                    return objectMapper.writeValueAsString(action);
                } catch (JsonProcessingException e) {
                    //   Log.info(e.getCause());
                    return null;
                }
            }).collect(Collectors.toList()));

            Path file = Paths.get(Configuration.OUT_DIR_PATH);
            //Todo: this is temporarry To remove
            //Path file = Paths.get(ConfigurationTMP.OUT_DIR_PATH);
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*File checkOpen = new File("MacroSave__"+saveFileName.getText()+"__.txt");
        if(!checkOpen.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream("MacroSave__" + saveFileName.getText() + "__.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(MouseListener.savedMouseState);
                oos.close();
                System.out.println("Done saving workflowRecorder.file");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            workflowRecorder.player.displayMessage.alertBox("File Saved", "Saved");
        }
        else {
            workflowRecorder.player.displayMessage.errorBox("File Already Exist", "Error");
        }*/
    }

    public void startOpen(ActionEvent actionEvent) {

       /* File checkOpen = new File("MacroSave__"+openFileName.getText()+"__.txt");
        if(checkOpen.exists()) {
            try{
                FileInputStream fis = new FileInputStream("MacroSave__"+openFileName.getText()+"__.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                MouseListener.savedMouseState = (List<MouseCoordinates>) ois.readObject();
                ois.close();
                System.out.println("Done opening workflowRecorder.file");
            }catch(Exception ex){
                ex.printStackTrace();
            }
            pressRecord.setVisible(true);
            pressStop.setVisible(false);
            pressPlay.setVisible(true);
            workflowRecorder.player.displayMessage.alertBox("File Opened", "Opened");
        }
        else {
            workflowRecorder.player.displayMessage.errorBox("File Does Not Exist", "Error");
        }*/
    }

    private void updateButtons(boolean flag) {
        pressRecord.setVisible(!flag);
        pressStop.setVisible(flag);
        //pressPlay.setVisible(!flag);
        //saveFileName.setVisible(!flag);
        //openFileName.setVisible(!flag);
        //pressSave.setVisible(!flag);
        //pressOpen.setVisible(!flag);
    }
}
