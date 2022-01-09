1.clean package
2.run cmd as administrator
3.execute
java -jar C:\Users\user\IdeaProjects\MouseRobot\target\MouseRobot-1.0-SNAPSHOT-jar-with-dependencies.jar


1 second contains 1000 ms
1 minute = 60 seconds = 60 000ms
5 min = 300 000

Controls:

1 - start/pause recording (actual recording is not started, but just armed for record.
 The actual recording starts after first click or first key press is done)
2 - save recorded workflow (currently workflow is saved into file with name default.txt)
3 - execute workflow (currently workflow with name default is executed)
4 - insert special action (Currently generic - copy to clipboard, execute file etc.)

* After application is armed to record the mouse and keyboard events are not recorded yet. The recoding starts only after
first click is done or first key (except control keys) is pressed.
After first click or key press the timer starts which is used to calculate delay duration between actions.
When recording is paused timer stops, events are not recorded anymore. After recording is armed again recording is started
also after firs click or key press, also only after the timer is resumed.