package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarController implements Initializable {

    @FXML private GridPane miniCalendar;

    @FXML private CheckBox selectDaily;

    @FXML private CheckBox selectWeekly;

    @FXML private Label monYearLabel;

    @FXML private Label selectedDate;

    @FXML private AnchorPane addPane;

    @FXML private AnchorPane agendaPane;

    @FXML private CheckBox eventCheck;

    @FXML private CheckBox taskCheck;

    @FXML private TextField eventNameBox;

    @FXML private TextField dateBox;

    @FXML private TextField sTimeBox;

    @FXML private TextField eTimeBox;

    @FXML private CheckBox agendaBox;

    @FXML private CheckBox viewEvent;

    @FXML private CheckBox viewTask;

    @FXML private ListView listedEvents;

    @FXML private ComboBox changeYearBox;

    @FXML private AnchorPane tablePane;

    @FXML private Button removeButton;

    @FXML private Button doneButton;

    @FXML private TableView<TimeSlot> dailyView;

    @FXML private TableColumn<TimeSlot, String> timeColumn;

    @FXML private TableColumn<TimeSlot, String> eventColumn;

    @FXML
    private void changeYear() {
        yearToday = Integer.parseInt(changeYearBox.getSelectionModel().getSelectedItem().toString());
        refreshCalendar(monthToday, yearToday);
    }

    @FXML
    private void viewAll() {
        tablePane.setVisible(false);
        listedEvents.getItems().clear();
        String[] date = monYearLabel.getText().split(" ");
        ArrayList<Occurrence> events = new ArrayList<>();
        boolean both = false;
        Text a = new Text();

        arrangeEvents();

        if(agendaBox.isSelected()) {
            agendaPane.setVisible(true);
            listedEvents.setVisible(true);

            if(selectDaily.isSelected()) {
                a.setText("Daily Agenda");
                a.setFont(Font.font("Avenir 85 Heavy", 14));
                listedEvents.getItems().add(a);
            }
            else if(selectWeekly.isSelected()) {
                a.setText("Weekly Agenda");
                a.setFont(Font.font("Avenir 85 Heavy", 14));
                listedEvents.getItems().add(a);
            }

            if(viewEvent.isSelected() && viewTask.isSelected())
                both = true;

            // If the user wants to see only events in a selected date
            if (selectDaily.isSelected() && viewEvent.isSelected() && !both) {
                for (Occurrence o : cModel.getEventlist().getEvents())
                    if (isToday(o))
                        if(o instanceof Event)
                            events.add(o);

                listEvents(events, true, true, false);
            }

            // If the user wants to see only tasks in a selected date
            else if(selectDaily.isSelected() && viewTask.isSelected() && !both) {
                for (Occurrence o : cModel.getEventlist().getEvents())
                    if (isToday(o))
                        if(o instanceof Task)
                            events.add(o);

                listEvents(events, true, false, false);
            }

            // If the user wants to see tasks and events in a selected day
            else if(selectDaily.isSelected() && viewTask.isSelected() && viewEvent.isSelected() && both) {
                for (Occurrence o : cModel.getEventlist().getEvents())
                    if (isToday(o))
                        events.add(o);

                listEvents(events, true, false, true);
            }
        }
    }

    @FXML
    private void checkTask() {
        taskCheck.setSelected(true);
        eventCheck.setSelected(false);
        eTimeBox.setDisable(true);
    }

    @FXML
    private void checkEvent() {
        eventCheck.setSelected(true);
        taskCheck.setSelected(false);
        eTimeBox.setDisable(false);
    }

    @FXML
    private void addEvent() {
        dateBox.setText("");
        sTimeBox.setText("");
        eTimeBox.setText("");
        agendaPane.setVisible(false);
        listedEvents.setVisible(false);
        tablePane.setVisible(false);

        eventNameBox.setText("");
        eventCheck.setSelected(false);
        taskCheck.setSelected(false);

        addPane.setVisible(true);
        selectDaily.setSelected(false);
        selectWeekly.setSelected(false);

        clearMiniCalendar();
    }

    @FXML
    private void markEvent() {

        String[] event = ((Text) listedEvents.getSelectionModel().getSelectedItem()).getText().split(" ");
        StringBuilder selectedEvent = new StringBuilder();

        for(int i = 1; i < event.length; i++) {
            selectedEvent.append(event[i]);
            if(i < event.length-1)
                selectedEvent.append(" ");
        }

        eSelected = selectedEvent.toString();

        // If the event has the same name and the event is a task
        for(Occurrence o : cModel.getEventlist().getEvents()) {
            if(o.getName().contentEquals(selectedEvent)) {
                if (o instanceof Task) {
                    doneButton.setVisible(true);
                    removeButton.setVisible(true);
                }
            }
        }
    }

    @FXML
    private void removeEvent() {
        ArrayList<Occurrence> tasks = new ArrayList<>();
        boolean both = false;
        boolean taskOnly = false;
        boolean daily = false;

        cModel.getEventlist().removeOccurrence(eSelected);

        for(Occurrence o : cModel.getEventlist().getEvents())
            if(o instanceof Task)
                tasks.add(o);

        if(viewEvent.isSelected() && !viewTask.isSelected())
            taskOnly = false;
        else if(!viewEvent.isSelected() && viewTask.isSelected())
            taskOnly = true;
        else
            both = true;

        if(selectDaily.isSelected())
            daily = true;

        cModel.getCsvParser().reWriteData(tasks);
        listEvents(cModel.getEventlist().getEvents(), daily, taskOnly, both);

        removeButton.setVisible(false);
        doneButton.setVisible(false);
        arrangeEvents();

    }

    @FXML
    private void markAsDone() {
        boolean both = false;
        boolean taskOnly = false;
        boolean daily = false;

        cModel.getEventlist().markOccurrence(eSelected);

        if (!viewTask.isSelected())
            if (viewEvent.isSelected()) {
                taskOnly = false;
            } else {
                if (!viewEvent.isSelected() && viewTask.isSelected())
                    taskOnly = true;
                else
                    both = true;
            }
        else if (!viewEvent.isSelected() && viewTask.isSelected())
            taskOnly = true;
        else
            both = true;

        if(selectDaily.isSelected())
            daily = true;

        listEvents(cModel.getEventlist().getEvents(), daily, taskOnly, both);

        removeButton.setVisible(false);
        doneButton.setVisible(false);
    }

    @FXML
    private void saveEvent() {
        if(eventCheck.isSelected()) {
            String[] date = dateBox.getText().split("/");
            String[] startTime = sTimeBox.getText().split(":");
            String[] endTime = eTimeBox.getText().split(":");
            Event e = new Event(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]), eventNameBox.getText(),
                    "green", Classification.toClassification("event"),
                    Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]),
                    Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]));
            if(cModel.isValid(e)) {
                cModel.getEventlist().addOccurrence(e);
                cModel.getPsvParser().appendEvent(e);
            }
            else {
                Parent viewParent;
                final Stage invalid = new Stage();
                invalid.initModality(Modality.APPLICATION_MODAL);

                invalidPopUp(invalid);
            }

        }
        else {
            String[] date = dateBox.getText().split("/");
            String[] startTime = sTimeBox.getText().split(":");

            Task t = new Task(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]), eventNameBox.getText(),
                    "blue", Classification.toClassification("task"),
                    Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]),
                    Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]));
            if(cModel.isValid(t)) {
                cModel.getEventlist().addOccurrence(t);
                cModel.getCsvParser().appendEvent(t);
            }
            else {
                Parent viewParent;
                final Stage invalid = new Stage();
                invalid.initModality(Modality.APPLICATION_MODAL);

                invalidPopUp(invalid);
            }
        }
        addPane.setVisible(false);

        clearMiniCalendar();
    }

    private void invalidPopUp(Stage invalid) {
        Parent viewParent;
        try {
            viewParent = FXMLLoader.load(getClass().getResource("invalid.fxml"));
            Scene sc = new Scene(viewParent);

            invalid.setScene(sc);
            invalid.show();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        addPane.setVisible(false);
        clearMiniCalendar();
    }

    @FXML
    private void nextMonth() {
        if(monthToday == 11) {
            monthToday = 0;
            yearToday += 1;
        }

        else
            monthToday += 1;

        monYearLabel.setText(convert(monthToday) + " " + yearToday);
        refreshCalendar(monthToday, yearToday);
    }

    @FXML
    private void prevMonth() {
        if(monthToday == 0) {
            monthToday = 11;
            yearToday -= 1;
        }

        else
            monthToday -= 1;

        monYearLabel.setText(convert(monthToday) + " " + yearToday);
        refreshCalendar(monthToday, yearToday);
    }

    @FXML
    private void checkDaily() {
        if(!agendaBox.isSelected()) {
            if(viewEvent.isSelected())
                listEventsToday(false, false);
            else if(viewTask.isSelected())
                listEventsToday(true, false);
            else if(viewTask.isSelected() && viewEvent.isSelected()) {
                listEventsToday(false, true);
            }
            tablePane.setVisible(true);
            addPane.setVisible(false);
        }
        selectDaily.setSelected(true);
        selectWeekly.setSelected(false);
    }

    @FXML
    private void checkWeekly() {
        selectWeekly.setSelected(true);
        selectDaily.setSelected(false);
        addPane.setVisible(false);
    }

    @FXML
    private void taskOnly() {

        // If the user wants to see only the tasks
        if(agendaBox.isSelected() && selectDaily.isSelected())
            if(viewTask.isSelected() && !viewEvent.isSelected())
                listEvents(cModel.getEventlist().getEvents(), true, false, false);

        // If the user wants to see both tasks and events
        if(agendaBox.isSelected() && selectDaily.isSelected())
            if(viewTask.isSelected() && viewEvent.isSelected())
                listEvents(cModel.getEventlist().getEvents(), true, false, true);

        // If the user deselected the tasks while looking for events
        if(agendaBox.isSelected() && selectDaily.isSelected())
            if(!viewTask.isSelected() && viewEvent.isSelected())
                listEvents(cModel.getEventlist().getEvents(), true, true, false);

        // If the user deselected both
        clearList();

        // If the user wants to see tasks only in the daily view
        if(!agendaBox.isSelected() && selectDaily.isSelected())
            if(viewTask.isSelected() && !viewEvent.isSelected())
                listEventsToday(true, false);

        // If the user wants to see both events and tasks in the daily view
        if(!agendaBox.isSelected() && selectDaily.isSelected())
            if (viewTask.isSelected() && viewEvent.isSelected())
                listEventsToday(false, true);

        // If the user deselects the tasks and wants to see events only
        if(!agendaBox.isSelected() && selectDaily.isSelected())
            if (!viewTask.isSelected() && viewEvent.isSelected())
                listEventsToday(false, false);
    }

    @FXML
    private void eventOnly() {

        // If the user wants to see only the events
        if(agendaBox.isSelected() && selectDaily.isSelected())
            if(viewEvent.isSelected() && !viewTask.isSelected())
                listEvents(cModel.getEventlist().getEvents(), true, true, false);

        // If the user wants to see both events and tasks
        if(agendaBox.isSelected() && selectDaily.isSelected())
            if(viewEvent.isSelected() && viewTask.isSelected())
                listEvents(cModel.getEventlist().getEvents(), true, false, true);

        // If the user deselected the events while looking for tasks
        if(agendaBox.isSelected() && selectDaily.isSelected())
            if(!viewEvent.isSelected() && viewTask.isSelected())
                listEvents(cModel.getEventlist().getEvents(), true, false, false);

        // If the user deselected both
        clearList();

        // If the user wants to see events only in the daily view
        if(!agendaBox.isSelected() && selectDaily.isSelected())
            if(viewEvent.isSelected() && !viewTask.isSelected())
                listEventsToday(false, false);

        // If the user wants to see both events and tasks in the daily view
        if(!agendaBox.isSelected() && selectDaily.isSelected())
            if (viewEvent.isSelected() && viewTask.isSelected())
                listEventsToday(false, true);

        // If the user deselects the events and wants to see tasks only
        if(!agendaBox.isSelected() && selectDaily.isSelected())
            if (!viewEvent.isSelected() && viewTask.isSelected())
                listEventsToday(true, false);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cModel = new CalendarModel();

        daySelected = 1;

        Calendar cal = Calendar.getInstance();
        int yearBound = cal.get(GregorianCalendar.YEAR);
        monthToday = cal.get(GregorianCalendar.MONTH);
        dayToday = cal.get(GregorianCalendar.DAY_OF_WEEK);
        yearToday = yearBound;

        selectedDate.setText(convertFullMonth(convert(monthToday)) + " " + cal.get(Calendar.DAY_OF_MONTH) + ", " + cal.get(Calendar.YEAR));

        addPane.setVisible(false);
        agendaPane.setVisible(false);
        listedEvents.setVisible(false);
        tablePane.setVisible(false);

        changeYearBox.setValue(String.valueOf(yearToday));

        for(int i = yearToday - 100; i < yearToday + 100; i++)
            changeYearBox.getItems().add(String.valueOf(i));

        doneButton.setVisible(false);
        removeButton.setVisible(false);

        arrangeEvents();
        refreshCalendar(monthToday, yearToday);
    }

    private void refreshCalendar(int month, int year) {
        miniCalendar.getChildren().clear();

        monYearLabel.setText(convert(month) + " " + year);
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int som = cal.get(GregorianCalendar.DAY_OF_WEEK);

        for(int i = 1; i <= nod; i++) {
            int row = (i + som - 2) / 7;
            int column  =  (i + som - 2) % 7;

            Button button = new Button(String.valueOf(i));
            button.setMaxSize(28,35);
            button.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color: transparent; -fx-text-fill: #FFFFFF");
            int finalI = i;
            button.setOnAction(event -> {
                daySelected = Integer.parseInt(((Button) event.getSource()).getText());
                button.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color:  #dc654d; -fx-text-fill: #FFFFFF");
                String[] my = monYearLabel.getText().split(" ");
                dateBox.setText( convertFromString(my[0]) + "/" + daySelected + "/" + my[1]);
                selectedDate.setText(convertFullMonth(convert(monthToday)) + " " + daySelected + ", " + my[1]);
                for(Node node : miniCalendar.getChildren()) {
                    if (node instanceof Button && Integer.parseInt(((Button) node).getText()) != daySelected)
                        node.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color: transparent; -fx-text-fill: #FFFFFF");
                }
                dateBox.setDisable(true);
            });

            for(Occurrence o: cModel.getEventlist().getEvents()) {
                if(eventToday(o, i)) {
                    button.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color: transparent; -fx-text-fill: #26FF25");
                    break;
                }
            }

            miniCalendar.add(button, column, row);
        }
    }

    private void listEvents(ArrayList<Occurrence> o, boolean daily, boolean eventOnly, boolean both) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String[] time = dateFormat.format(cal.getTime()).split(" ");
        String[] splitTime = time[1].split(":");
        int hour = Integer.parseInt(splitTime[0]);
        int min = Integer.parseInt(splitTime[0]);

        listedEvents.getItems().clear();

        if(daily) {
            Text a = new Text();
            a.setText("Daily Agenda");
            a.setFont(Font.font("Avenir 85 Heavy", 14));
            listedEvents.getItems().add(a);
            for(Occurrence occ : o) {
                if (isToday(occ)) {
                    if(eventOnly && !both) {
                        if (occ instanceof Event) {
                            checkExpired(hour, occ);
                        }
                    }

                    else if(!eventOnly && !both) {
                        addTask(occ);
                    }

                    else {
                        if (occ instanceof Event) {
                            checkExpired(hour, occ);
                        }
                        else
                            addTask(occ);
                    }

                }
            }
        }
    }

    private void checkExpired(int hour, Occurrence occ) {
        if(occ.getStartHour() >= hour) {
            Text t = new Text(occ.getStartHour() + ":" + convertMin(occ.getStartMin()) + "-" + occ.getEndHour() + ":" + convertMin(occ.getEndMin()) + " " + occ.getName());
            t.setFont(Font.font("Avenir 85 Heavy", 14));
            t.setFill(Color.GREEN);
            listedEvents.getItems().add(t);
        }
    }

    private void addTask(Occurrence occ) {
        if(occ instanceof Task) {
            Text t = new Text(occ.getStartHour() + ":" + convertMin(occ.getStartMin()) + "-" + occ.getEndHour() + ":" + convertMin(occ.getEndMin()) + " " + occ.getName());
            t.setFont(Font.font("Avenir 85 Heavy", 14));
            t.setFill(Color.BLUE);
            if (((Task) occ).isMarked()) {
                t.setStrikethrough(true);
                t.setFill(Color.GRAY);
            }
            listedEvents.getItems().add(t);
        }
    }

    private void listEventsToday(boolean taskOnly, boolean both) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<TimeSlot, String>("time"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<TimeSlot, String>("name"));
        eventColumn.setCellFactory(column -> {
            return new TableCell<TimeSlot, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    }

                    else {

                        setText(item);
                        TimeSlot sample = getTableView().getItems().get(getIndex());

                        if (sample.getClassification().equals("task")) {
                            setText(sample.getName());
                            setTextFill(Color.WHITE);
                            setStyle("-fx-background-color: blue;");
                        }
                        else if (sample.getClassification().equals("event")){
                            setText(sample.getName());
                            setTextFill(Color.WHITE);
                            setStyle("-fx-background-color: green");
                        }
                    }
                }
            };
        });

        dailyView.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            dailyView.refresh();
            dailyView.edit(-1, null);
        });
        dailyView.setItems(getEvents(taskOnly, both));
    }

    private boolean eventToday(Occurrence o, int day) {
        String[] date = monYearLabel.getText().split(" ");
        return o.getDate().get(Calendar.YEAR) == Integer.parseInt(date[1]) &&
                o.getDate().get(Calendar.MONTH) == (convertFromString(date[0]) - 1) &&
                o.getDate().get(Calendar.DAY_OF_MONTH) == day;
    }

    private ObservableList<TimeSlot> getEvents(boolean taskOnly, boolean both) {
        int j = 0;
        int min = 0;
        ObservableList<TimeSlot> events = FXCollections.observableArrayList();

        for(int i = 0; i < 48; i++) {

            TimeSlot t = new TimeSlot(j + ":00", "", "");

            // Search if there is an event at time
            for(Occurrence o : cModel.getEventlist().getEvents()) {
                if(isToday(o)) {
                    if(o.getStartHour() == j && o.getStartMin() == min) {
                        if(!taskOnly && !both) {
                            if(o instanceof Event) {
                                t.setName(o.getName());
                                t.setClassification("event");
                                break;
                            }
                        }

                        else if(taskOnly && !both) {
                            if(o instanceof Task) {
                                t.setName(o.getName());
                                t.setClassification("task");
                                break;
                            }
                        }

                        else if(!taskOnly) {
                            if(o instanceof Event) {
                                t.setName(o.getName());
                                t.setClassification("event");
                                break;
                            }

                            else if(o instanceof Task) {
                                t.setName(o.getName());
                                t.setClassification("task");
                                break;
                            }
                        }
                    }

                    else if (o.getStartHour() < j && o.getEndHour() > j || (o.getStartHour() == j) && min == 30) {
                        if (classify(taskOnly, both, t, o)) break;
                    }

                    else if(o.getEndHour() == j && o.getEndMin() == min) {
                        if (classify(taskOnly, both, t, o)) break;
                    }
                }

            }

            if(i % 2 != 0) {
                t.setTime("");
                j++;
            }

            if(min == 0)
                min = 30;
            else
                min = 0;

            events.add(t);
        }

        return events;
    }

    private boolean classify(boolean taskOnly, boolean both, TimeSlot t, Occurrence o) {
        if(!taskOnly && !both) {
            if(o instanceof Event) {
                t.setClassification("event");
                return true;
            }
        }

        else if(taskOnly && !both) {
            if(o instanceof Task) {
                t.setClassification("task");
                return true;
            }
        }

        else if(!taskOnly) {
            if(o instanceof Event) {
                t.setClassification("event");
                return true;
            }

            else if(o instanceof Task) {
                t.setClassification("task");
                return true;
            }
        }
        return false;
    }

    private void arrangeEvents() {

        for(int i = 0; i < cModel.getEventlist().getEvents().size(); i++) {
            for(int j = 0; j < cModel.getEventlist().getEvents().size(); j++) {
                if(cModel.getEventlist().getEvents().get(i).getStartHour() < cModel.getEventlist().getEvents().get(j).getStartHour())
                    Collections.swap(cModel.getEventlist().getEvents(), i, j);
                else if(cModel.getEventlist().getEvents().get(i).getStartHour() == cModel.getEventlist().getEvents().get(j).getStartHour())
                    if(cModel.getEventlist().getEvents().get(i).getStartMin() < cModel.getEventlist().getEvents().get(i).getStartMin())
                        Collections.swap(cModel.getEventlist().getEvents(), i, j);
            }
        }
    }

    private void clearList() {
        if(agendaBox.isSelected() && selectDaily.isSelected()) {
            if (!viewEvent.isSelected() && !viewTask.isSelected()) {
                listedEvents.getItems().clear();
                Text a = new Text();
                a.setText("Daily Agenda");
                a.setFont(Font.font("Avenir 85 Heavy", 14));
                listedEvents.getItems().add(a);
            }
        }
    }

    private boolean isToday(Occurrence o) {
        String[] date = monYearLabel.getText().split(" ");
        return o.getDate().get(Calendar.YEAR) == Integer.parseInt(date[1]) &&
                o.getDate().get(Calendar.MONTH) == (convertFromString(date[0]) - 1) &&
                o.getDate().get(Calendar.DAY_OF_MONTH) == daySelected;
    }

    private void clearMiniCalendar() {
        for(Node node : miniCalendar.getChildren())
            if(node instanceof Button)
                node.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color: transparent; -fx-text-fill: #FFFFFF");
    }

    private String convertMin(int min) {
        if(min == 0)
            return "00";

        return "30";
    }

    private String convert(int month) {
        switch(month) {
            case 0: return "Jan";
            case 1: return "Feb";
            case 2: return "Mar";
            case 3: return "Apr";
            case 4: return "May";
            case 5: return "Jun";
            case 6: return "Jul";
            case 7: return "Aug";
            case 8: return "Sept";
            case 9: return "Oct";
            case 10: return "Nov";
            case 11: return "Dec";
        }
        return "January";
    }

    private int convertFromString(String month) {
        switch(month) {
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sept": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
        }
        return 1;
    }

    private String convertFullMonth(String month) {
        switch(month) {
            case "Jan": return "January";
            case "Feb": return "February";
            case "Mar": return "March";
            case "Apr": return "April";
            case "May": return "May";
            case "Jun": return "June";
            case "Jul": return "July";
            case "Aug": return "August";
            case "Sept": return "September";
            case "Oct": return "October";
            case "Nov": return "November";
            case "Dec": return "December";
        }
        return "January";
    }

    private int yearToday;
    private int monthToday;
    private int dayToday;
    private CalendarModel cModel;
    private int daySelected;
    private String eSelected;

}
