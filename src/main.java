// ReservationSystemGUI.java
// JavaFX ile GUI destekli uçak/otobüs rezervasyon sistemi (tasarım desenleri kullanılarak)

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import singleton.SessionManager;
import service.*;
import factory.*;
import adapter.*;
import command.*;
import observer.*;

import java.util.*;

public class ReservationSystemGUI extends Application {
    private UserService userService = new UserService();
    private TripService tripService = new TripService();
    private ReservationService reservationService = new ReservationService();
    private VBox mainLayout = new VBox(10);
    private Scene mainScene;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        seedData();
        showLoginScreen();
    }

    private void seedData() {
        userService.register("irem", false);
        userService.register("admin", true);
        tripService.addTrip(TripFactory.createTrip("bus", "B001", "Izmir", "Ankara"));
        tripService.addTrip(TripFactory.createTrip("flight", "F001", "Istanbul", "Berlin"));
    }

    private void showLoginScreen() {
        VBox loginBox = new VBox(10);
        TextField usernameField = new TextField();
        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");

        loginBtn.setOnAction(e -> {
            User user = userService.login(usernameField.getText());
            if (user != null) {
                SessionManager.getInstance().login(user);
                showDashboard();
            } else {
                showAlert("Login Failed", "User not found");
            }
        });

        registerBtn.setOnAction(e -> {
            userService.register(usernameField.getText(), false);
            showAlert("Success", "User Registered");
        });

        loginBox.getChildren().addAll(new Label("Username:"), usernameField, loginBtn, registerBtn);
        mainScene = new Scene(loginBox, 400, 200);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Reservation System");
        primaryStage.show();
    }

    private void showDashboard() {
        mainLayout.getChildren().clear();
        User user = SessionManager.getInstance().getCurrentUser();
        Label welcome = new Label("Welcome, " + user.getUsername());
        Button viewTripsBtn = new Button("View Trips");
        Button logoutBtn = new Button("Logout");

        logoutBtn.setOnAction(e -> {
            SessionManager.getInstance().logout();
            showLoginScreen();
        });

        viewTripsBtn.setOnAction(e -> showTripList());
        mainLayout.getChildren().addAll(welcome, viewTripsBtn);

        if (user.isAdmin()) {
            Button addTripBtn = new Button("Add Trip");
            addTripBtn.setOnAction(e -> showAdminPanel());
            mainLayout.getChildren().add(addTripBtn);
        }

        mainLayout.getChildren().add(logoutBtn);
        mainScene.setRoot(mainLayout);
    }

    private void showTripList() {
        VBox tripBox = new VBox(10);
        for (Trip trip : tripService.getAllTrips()) {
            Button tripBtn = new Button(trip.getId() + " - " + trip.getOrigin() + " to " + trip.getDestination());
            tripBtn.setOnAction(e -> showSeatSelection(trip));
            tripBox.getChildren().add(tripBtn);
        }
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> showDashboard());
        tripBox.getChildren().add(backBtn);
        mainScene.setRoot(tripBox);
    }

    private void showSeatSelection(Trip trip) {
        GridPane seatGrid = new GridPane();
        List<Seat> seats = trip.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            Button seatBtn = new Button("" + (i + 1));
            if (seat.isReserved()) seatBtn.setStyle("-fx-background-color: red");
            else seatBtn.setStyle("-fx-background-color: green");

            int row = i / 5;
            int col = i % 5;
            seatGrid.add(seatBtn, col, row);

            seatBtn.setOnAction(e -> {
                Reservation reservation = new Reservation(SessionManager.getInstance().getCurrentUser(), trip, seat);
                Command reserveCommand = new ReserveSeatCommand(reservation);
                reserveCommand.execute();
                showAlert("Success", "Seat Reserved");
                showTripList();
            });
        }

        VBox layout = new VBox(10);
        Button back = new Button("Back");
        back.setOnAction(e -> showTripList());
        layout.getChildren().addAll(new Label("Select a Seat:"), seatGrid, back);
        mainScene.setRoot(layout);
    }

    private void showAdminPanel() {
        VBox adminPanel = new VBox(10);
        TextField idField = new TextField();
        TextField originField = new TextField();
        TextField destinationField = new TextField();
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("bus", "flight");
        Button addBtn = new Button("Add Trip");

        addBtn.setOnAction(e -> {
            Trip trip = TripFactory.createTrip(typeBox.getValue(), idField.getText(), originField.getText(), destinationField.getText());
            tripService.addTrip(trip);
            showAlert("Success", "Trip Added");
            showDashboard();
        });

        adminPanel.getChildren().addAll(new Label("Trip ID:"), idField,
                                        new Label("Origin:"), originField,
                                        new Label("Destination:"), destinationField,
                                        new Label("Type:"), typeBox,
                                        addBtn);

        mainScene.setRoot(adminPanel);
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

