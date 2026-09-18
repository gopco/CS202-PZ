package rs.ac.metropolitan.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rs.ac.metropolitan.project.model.Hotel;
import rs.ac.metropolitan.project.model.Reservation;
import rs.ac.metropolitan.project.repository.AgencyRepository;
import rs.ac.metropolitan.project.web.WeatherInfo;
import rs.ac.metropolitan.project.web.WeatherInfoService;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;

/**
 * Controller for agency form
 */
public class AgencyController {
    private final AgencyRepository repository;
    private final WeatherInfoService weatherInfoService;

    @FXML
    private TableView<Hotel> hotelTable;
    @FXML
    private TableColumn<Hotel, String> nameColumn;
    @FXML
    private TableColumn<Hotel, String> addressColumn;
    @FXML
    private TableColumn<Hotel, String> contactInformationColumn;
    @FXML
    private TableColumn<Hotel, Integer> numberOfRoomsColumn;
    @FXML
    private TableColumn<Hotel, Double> priceColumn;
    @FXML
    private TableColumn<Hotel, Double> ratingColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField contactInformationField;
    @FXML
    private TextField numberOfRoomsField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField ratingField;
    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, LocalDate> startDateColumn;
    @FXML
    private TableColumn<Reservation, LocalDate> endDateColumn;
    @FXML
    private TableColumn<Reservation, Integer> reservationNumberOfRoomsColumn;
    @FXML
    private TableColumn<Reservation, String> reservationContactInformationColumn;
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private TextField reservationNumberOfRoomsField;
    @FXML
    private TextField reservationContactInformationField;
    @FXML
    private Label weatherInfoLabel;
    private ExecutorService executorService;

    /**
     * Creates a new instance of {@link AgencyController} and initializes repository and weather service
     *
     * @param repository         repository
     * @param weatherInfoService weather service
     */
    public AgencyController(AgencyRepository repository, WeatherInfoService weatherInfoService) {
        this.repository = repository;
        this.weatherInfoService = weatherInfoService;
    }

    /**
     * Initializes the controller
     */
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        contactInformationColumn.setCellValueFactory(new PropertyValueFactory<>("contactInformation"));
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        reservationNumberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        reservationContactInformationColumn.setCellValueFactory(new PropertyValueFactory<>("contactInformation"));

        hotelTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameField.setText(newValue.getName());
                addressField.setText(newValue.getAddress());
                contactInformationField.setText(newValue.getContactInformation());
                numberOfRoomsField.setText(Integer.toString(newValue.getNumberOfRooms()));
                priceField.setText(Double.toString(newValue.getPrice()));
                ratingField.setText(Double.toString(newValue.getRating()));

                refreshReservations(newValue, null);
            }
        });

        reservationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                startDateField.setText(newValue.getStartDate().toString());
                endDateField.setText(newValue.getEndDate().toString());
                reservationNumberOfRoomsField.setText(Integer.toString(newValue.getNumberOfRooms()));
                reservationContactInformationField.setText(newValue.getContactInformation());
            }
        });

        refreshHotels(null);
        this.executorService = weatherInfoService.scheduleWeatherInfoRefresh(this::updateWeather);
    }

    /**
     * Updates the weather info
     *
     * @param weatherInfo weather info
     */
    private void updateWeather(WeatherInfo weatherInfo) {
        Platform.runLater(() -> weatherInfoLabel.setText("Current temperature is " + weatherInfo.temperature()));
    }

    /**
     * Stops the executor service
     */
    public void stop() {
        this.executorService.shutdownNow();
    }

    /**
     * Button event handler for new hotel
     */
    public void onNewHotel() {
        var hotel = new Hotel();
        repository.save(hotel);
        refreshHotels(hotel);
    }

    /**
     * Button event handler for save hotel
     */
    public void onSaveHotel() {
        var hotel = hotelTable.getSelectionModel().getSelectedItem();
        if (hotel == null) {
            return;
        }
        hotel.setName(nameField.getText());
        hotel.setAddress(addressField.getText());
        hotel.setContactInformation(contactInformationField.getText());
        hotel.setNumberOfRooms(Integer.parseInt(numberOfRoomsField.getText()));
        hotel.setPrice(Double.parseDouble(priceField.getText()));
        hotel.setRating(Double.parseDouble(ratingField.getText()));
        repository.save(hotel);
        refreshHotels(hotel);
    }

    /**
     * Button event handler for delete hotel
     */
    public void onDeleteHotel() {
        var hotel = hotelTable.getSelectionModel().getSelectedItem();
        if (hotel == null) {
            return;
        }
        repository.delete(hotel);
        refreshHotels(null);
    }

    /**
     * Button event handler for new reservation
     */
    public void onNewReservation() {
        var hotel = hotelTable.getSelectionModel().getSelectedItem();
        if (hotel == null) {
            return;
        }
        var reservation = new Reservation();
        reservation.setHotel(hotel);
        hotel.getReservations().add(reservation);
        repository.save(reservation);
        refreshHotels(hotel);
        refreshReservations(hotel, reservation);
    }

    /**
     * Button event handler for save reservation
     */
    public void onSaveReservation() {
        var reservation = reservationTable.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            return;
        }
        reservation.setStartDate(LocalDate.parse(startDateField.getText()));
        reservation.setEndDate(LocalDate.parse(endDateField.getText()));
        reservation.setNumberOfRooms(Integer.parseInt(reservationNumberOfRoomsField.getText()));
        reservation.setContactInformation(reservationContactInformationField.getText());
        repository.save(reservation);
        refreshReservations(reservation.getHotel(), reservation);
    }

    /**
     * Button event handler for delete reservation
     */
    public void onDeleteReservation() {
        var reservation = reservationTable.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            return;
        }
        var hotel = reservation.getHotel();
        repository.delete(reservation);
        refreshHotels(hotel);
        refreshReservations(hotel, null);
    }

    /**
     * Refreshes the hotel table
     *
     * @param select the hotel to select
     */
    private void refreshHotels(Hotel select) {
        var hotels = repository.findAll();
        hotelTable.getItems().clear();
        hotelTable.getItems().addAll(hotels);
        if (select != null) {
            hotelTable.getSelectionModel().select(select);
        } else {
            hotelTable.getSelectionModel().selectFirst();
        }
    }

    /**
     * Refreshes the reservation table
     *
     * @param hotel  the hotel containing reservations
     * @param select the reservation to select
     */
    private void refreshReservations(Hotel hotel, Reservation select) {
        var reservations = hotel.getReservations();
        reservationTable.getItems().clear();
        reservationTable.getItems().addAll(reservations);
        if (select != null) {
            reservationTable.getSelectionModel().select(select);
        } else {
            reservationTable.getSelectionModel().selectFirst();
        }
    }
}