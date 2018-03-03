package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label currLabel;

    @FXML
    private ComboBox<String> yearCmb;

    @FXML
    private Label lastYear;

    @FXML
    private Label nxtYear;

    @FXML
    private GridPane calendarGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GregorianCalendar cal = new GregorianCalendar();
        yearBound = cal.get(GregorianCalendar.YEAR);
        monthBound = cal.get(GregorianCalendar.MONTH);
        dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        monthToday = monthBound;
        yearToday = yearBound;

        for(int i = yearBound - 100; i <= yearBound + 100; i++)
            yearCmb.getItems().add(String.valueOf(i));

        yearCmb.setValue(String.valueOf(yearToday));

        lastYear.setText(String.valueOf(yearToday-1));
        nxtYear.setText(String.valueOf(yearToday+1));

        currLabel.setText(convert(monthToday) + ", " + yearToday);

//        refreshCalendar(monthToday, yearToday);

    }

//    public void refreshCalendar(int month, int year) {
//        if(month == 0 && year <= yearBound-10)
//            prevButton.setDisable(true);
//
//        if(month == 11 && year >= yearBound+100)
//            nextButton.setDisable(true);
//
//        lastYear.setText(String.valueOf(year-1));
//        nxtYear.setText(String.valueOf(year+1));
//
//        currLabel.setText(convert(month) + ", " + year);
//
//        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
//        int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
//        int som = cal.get(GregorianCalendar.DAY_OF_WEEK);
//
//        for(int i = 1; i <= nod; i++) {
//
//            int row = (i + som - 2) / 7;
//            int column  =  (i + som - 2) % 7;
//
//            for(Node node: calendarGrid.getChildren()) {
//                if(GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row &&
//                        GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null) {
//                    for(Node n : ((AnchorPane)node).getChildren())
//                        if(n instanceof Label)
//                            ((Label) n).setText(String.valueOf(i));
//                }
//            }
//        }
//
//
//    }

    private String convert(int month) {
        switch(month) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
        }
        return "January";
    }

    private int yearBound;
    private int monthBound;
    private int dayBound;
    private int yearToday;
    private int monthToday;

}
