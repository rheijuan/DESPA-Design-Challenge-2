package sample;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

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

    @FXML private TableView<TimeSlot> tableView;

    @FXML private AnchorPane tablePane;

    @FXML private TableColumn<TimeSlot, String> timeColumn;

    @FXML private TableColumn<TimeSlot, String> eventColumn;

    @FXML private Button removeButton;

    @FXML private Button doneButton;

    @FXML
    private void changeYear() {
        int selected = Integer.parseInt(changeYearBox.getSelectionModel().getSelectedItem().toString());
        refreshCalendar(monthToday, selected);
    }

    @FXML
    private void viewAll() {
        tablePane.setVisible(false);
        String[] date = monYearLabel.getText().split(" ");
        boolean both = false;

        if(agendaBox.isSelected()) {
            agendaPane.setVisible(true);
            listedEvents.getItems().clear();
            listedEvents.setVisible(true);
            Text a = new Text();

            if(viewEvent.isSelected() && viewTask.isSelected())
                both = true;

            // If the user wants to see only events in a selected date
            if (selectDaily.isSelected() && viewEvent.isSelected() && !both) {
                a.setText("Daily Agenda");
                a.setFont(Font.font("Avenir 85 Heavy", 14));
                listedEvents.getItems().add(a);
                for (Occurrence o : cModel.getEventlist().getEvents()) {
                    if (o.getDate().get(Calendar.YEAR) == Integer.parseInt(date[1]) &&
                            o.getDate().get(Calendar.MONTH) == (convertFromString(date[0]) - 1) &&
                            o.getDate().get(Calendar.DAY_OF_MONTH) == daySelected) {
                        if (o instanceof Event) {
                            Text t = new Text(o.getStartHour() + ":" + o.getStartMin() + "-" + o.getEndHour() + ":" + o.getEndMin() + " " + o.getName());
                            t.setFont(Font.font("Avenir 85 Heavy", 14));
                            t.setFill(Color.GREEN);
                            listedEvents.getItems().add(t);
                        }
                    }
                }
            }

            // If the user wants to see only tasks in a selected date
            else if(selectDaily.isSelected() && viewTask.isSelected() && !both) {
                a.setText("Daily Agenda");
                a.setFont(Font.font("Avenir 85 Heavy", 14));
                listedEvents.getItems().add(a);
                for (Occurrence o : cModel.getEventlist().getEvents()) {
                    if (o.getDate().get(Calendar.YEAR) == Integer.parseInt(date[1]) &&
                            o.getDate().get(Calendar.MONTH) == (convertFromString(date[0]) - 1) &&
                            o.getDate().get(Calendar.DAY_OF_MONTH) == daySelected) {
                        if (o instanceof Task) {
                            Text t = new Text(o.getStartHour() + ":" + o.getStartMin() + "-" + o.getEndHour() + ":" + o.getEndMin() + " " + o.getName());
                            t.setFont(Font.font("Avenir 85 Heavy", 14));
                            t.setFill(Color.BLUE);
                            listedEvents.getItems().add(t);
                        }
                    }
                }
            }

            // If the user wants to see tasks and events in a selected day
            else if(selectDaily.isSelected() && viewTask.isSelected() && viewEvent.isSelected() && both) {
                a.setText("Daily Agenda");
                a.setFont(Font.font("Avenir 85 Heavy", 14));
                listedEvents.getItems().add(a);
                for (Occurrence o : cModel.getEventlist().getEvents()) {
                    if (o.getDate().get(Calendar.YEAR) == Integer.parseInt(date[1]) &&
                            o.getDate().get(Calendar.MONTH) == (convertFromString(date[0]) - 1) &&
                            o.getDate().get(Calendar.DAY_OF_MONTH) == daySelected) {
                        if(o instanceof Event) {
                            Text t = new Text(o.getStartHour() + ":" + o.getStartMin() + "-" + o.getEndHour() + ":" + o.getEndMin() + " " + o.getName());
                            t.setFont(Font.font("Avenir 85 Heavy", 14));
                            t.setFill(Color.GREEN);
                            listedEvents.getItems().add(t);
                        }
                        else {
                            Text t = new Text(o.getStartHour() + ":" + o.getStartMin() + "-" + o.getEndHour() + ":" + o.getEndMin() + " " + o.getName());
                            t.setFont(Font.font("Avenir 85 Heavy", 14));
                            t.setFill(Color.BLUE);
                            listedEvents.getItems().add(t);
                        }
                    }
                }
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
        String[] selectedEvent = ((Text) listedEvents.getSelectionModel().getSelectedItem()).getText().split(" ");
        // If the event has the same name and the event is a task
        for(Occurrence o : cModel.getEventlist().getEvents()) {
            if(o.getName().equals(selectedEvent[1])) {
                if (o instanceof Task) {
                    doneButton.setVisible(true);
                    removeButton.setVisible(true);
                }
            }
        }
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
            if(cModel.isValid(e))
                cModel.getEventlist().addOccurrence(e);
            else
                System.out.println("Invalid");
        }
        else {
            String[] date = dateBox.getText().split("/");
            String[] startTime = sTimeBox.getText().split(":");

            Task t = new Task(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]), eventNameBox.getText(),
                    "blue", Classification.toClassification("task"),
                    Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]),
                    Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]));
            if(cModel.isValid(t))
                cModel.getEventlist().addOccurrence(t);
            else
                System.out.println("Invalid");
        }
        addPane.setVisible(false);
        clearMiniCalendar();
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
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));

        tableView.setItems(getEventsDaily());

        selectDaily.setSelected(true);
        selectWeekly.setSelected(false);
        tablePane.setVisible(true);
        addPane.setVisible(false);
    }

    @FXML
    private void checkWeekly() {
        selectWeekly.setSelected(true);
        selectDaily.setSelected(false);
        addPane.setVisible(false);
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
            button.setOnAction(event -> {
                daySelected = Integer.parseInt(((Button) event.getSource()).getText());
                button.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color:  #dc654d; -fx-text-fill: #FFFFFF");
                String[] my = monYearLabel.getText().split(" ");
                dateBox.setText( convertFromString(my[0]) + "/" + daySelected + "/" + my[1]);
                selectedDate.setText(convertFullMonth(convert(monthToday)) + " " + daySelected + ", " + my[1]);
                for(Node node : miniCalendar.getChildren())
                    if(node instanceof Button && Integer.parseInt(((Button) node).getText()) != daySelected)
                        node.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color: transparent; -fx-text-fill: #FFFFFF");
                dateBox.setDisable(true);
            });

            miniCalendar.add(button, column, row);
        }
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

    private ObservableList<TimeSlot> getEventsDaily() {
        ObservableList<TimeSlot> times = FXCollections.observableArrayList();
        int i = 0, j = 0;
        String eventName = "";

        while(i < 48) {

            if(i % 2 == 0) {
                for(Occurrence o : cModel.getEventlist().getEvents()) {
                    if(o.getDay().equals(monthToday + "/" + daySelected + "/" + yearToday)) {
                        if(o.getStartHour() == j && o.getStartMin() == 0) {

                        }
                    }
                }
                times.add(new TimeSlot((j + ":00"), eventName));
                j++;
            }
            else
                times.add(new TimeSlot("",eventName));
            i++;
        }

        return times;
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

    private void clearMiniCalendar() {
        for(Node node : miniCalendar.getChildren())
            if(node instanceof Button)
                node.setStyle("-fx-font-family: 'Avenir 85 Heavy'; -fx-font-size: 10px; -fx-background-color: transparent; -fx-text-fill: #FFFFFF");
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


}
