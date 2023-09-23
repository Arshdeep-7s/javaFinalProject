package com.example.project1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;


import java.io.*;

public class HospitalManagementApp extends Application {

    private ArrayList<Patient> patientsList = new ArrayList<>();
    private ArrayList<Appointment> appointmentsList = new ArrayList<>();
    private void addPatientToList(String firstName, String lastName, String email, String phone) {
        patientsList.add(new Patient(firstName, lastName, email, phone));
    }

    private Stage primaryStage;
    private Scene loginScene;
    private Scene createAccountScene;

    private Scene patientScene;
    private Scene managementScene;

    private Scene bookAppointmentScene;

    private Scene addPatientScene;
    private String loggedInPatient;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;



//        ------------------------------------- LOGIN SCENE ---------------------------------------------------
        this.primaryStage.setTitle("Hospital Management Login");

        Account.initializeAccountFile();


        GridPane loginGridPane = new GridPane();
        loginGridPane.setPadding(new Insets(20));
        loginGridPane.setHgap(10);
        loginGridPane.setVgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameTextField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label userTypeLabel = new Label("User Type:");
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("Patient", "Management");
        userTypeComboBox.setValue("Patient");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            String userType = userTypeComboBox.getValue();

            if (Account.validateLogin(username, password, userType)) {
                showAlert("Login Successful", "Welcome back, " + username + "!");
            } else {
                showAlert("Login Failed", "Invalid credentials. Please try again.");
                System.exit(1);
            }
            if (userType.equals("Patient")){
                loggedInPatient = username;
                showPatientScene();
            }
            if (userType.equals("Management"))
                showManagementScene();
        });


//        ----------------------------------- LOGIN SCENE ENDS ---------------------------------------------------------



        Button createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction(e -> {
            showCreateAccountScene();
        });

        loginGridPane.add(usernameLabel, 0, 0);
        loginGridPane.add(usernameTextField, 1, 0);
        loginGridPane.add(passwordLabel, 0, 1);
        loginGridPane.add(passwordField, 1, 1);
        loginGridPane.add(userTypeLabel, 0, 2);
        loginGridPane.add(userTypeComboBox, 1, 2);
        loginGridPane.add(loginButton, 1, 3);
        loginGridPane.add(createAccountButton, 1, 4);

        loginScene = new Scene(loginGridPane, 400, 250);

        // Create Account Form
        GridPane createAccountGridPane = new GridPane();
        createAccountGridPane.setPadding(new Insets(20));
        createAccountGridPane.setHgap(10);
        createAccountGridPane.setVgap(10);

        Label createUsernameLabel = new Label("New Username:");
        TextField createUsernameTextField = new TextField();

        Label createPasswordLabel = new Label("New Password:");
        PasswordField createPasswordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();

        Label createUserTypeLabel = new Label("User Type:");
        ComboBox<String> createUserTypeComboBox = new ComboBox<>();
        createUserTypeComboBox.getItems().addAll("Patient", "Management");
        createUserTypeComboBox.setValue("Patient");

        Button createAccountButton2 = new Button("Create Account");
        createAccountButton2.setOnAction(e -> {
            String newUsername = createUsernameTextField.getText();
            String newPassword = createPasswordField.getText();
            String confirmNewPassword = confirmPasswordField.getText();
            String newUserType = createUserTypeComboBox.getValue();

            if (Account.validateAccountCreation(newUsername, newPassword, confirmNewPassword, newUserType)) {
                Account.saveAccountData(newUsername, newPassword, newUserType);
                showAlert("Account Created", "Account created successfully for " + newUsername + "!");
                showLoginScene();
            } else {
                showAlert("Account Creation Failed", "Please check your input and try again.");
            }
        });

        Button backButton = new Button("Back to Login");
        backButton.setOnAction(e -> {
            showLoginScene();
        });

        createAccountGridPane.add(createUsernameLabel, 0, 0);
        createAccountGridPane.add(createUsernameTextField, 1, 0);
        createAccountGridPane.add(createPasswordLabel, 0, 1);
        createAccountGridPane.add(createPasswordField, 1, 1);
        createAccountGridPane.add(confirmPasswordLabel, 0, 2);
        createAccountGridPane.add(confirmPasswordField, 1, 2);
        createAccountGridPane.add(createUserTypeLabel, 0, 3);
        createAccountGridPane.add(createUserTypeComboBox, 1, 3);
        createAccountGridPane.add(createAccountButton2, 1, 4);
        createAccountGridPane.add(backButton, 1, 5);

        createAccountScene = new Scene(createAccountGridPane, 400, 300);

        primaryStage.setScene(loginScene);
        primaryStage.show();




//        --------------------------- BOOK APPOINTMENT START ---------------------------------------------
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label timeLabel = new Label("Time:");
        TextField timeTextField = new TextField();

        Button saveAppointmentButton = new Button("Save Appointment");
        saveAppointmentButton.setOnAction(e -> {
            String date = datePicker.getValue().toString();
            String time = timeTextField.getText();
            String patientName = usernameTextField.getText();
            saveAppointmentDetails(patientName, date, time);

            showPatientScene();
        });
        Button backtopatientButton = new Button("Go Back");
        backtopatientButton.setOnAction(e -> {
            showPatientScene();
        } );
        VBox bookAppointmentVBox = new VBox(10);
        bookAppointmentVBox.setPadding(new Insets(20));
        bookAppointmentVBox.getChildren().addAll(dateLabel, datePicker, timeLabel, timeTextField, saveAppointmentButton, backtopatientButton );


        BorderPane bookAppointmentBorderPane = new BorderPane();
        bookAppointmentBorderPane.setCenter(bookAppointmentVBox);
        bookAppointmentScene = new Scene(bookAppointmentBorderPane, 400, 250);


        Button bookAppointmentButton = new Button("Book appointment");
        bookAppointmentButton.setOnAction(e -> {
            showBookAppointmentScene();
        } );

//        ------------------------------- BOOK APPOINTMENT END ----------------------------------------------------------   //





//        --------------------------------- Reschedule APPOINTMENT START ------------------------------------------------

        Button rescheduleAppointmentButton = new Button("ReSchedule Appointment");
        rescheduleAppointmentButton.setOnAction(e -> {
            //showAlert("Reschedule Appointment", "Go to book appointment and select your new date and time. Thank you!!" );
            showBookAppointmentScene();
        } );

//        --------------------------------- Reschedule APPOINTMENT END ------------------------------------------------



//        ---------------------------------- CANCEL APPOINTMENT START --------------------------------------------------
        Button cancelAppointmentButton = new Button("Cancel Appointment");
        cancelAppointmentButton.setOnAction(e -> {
            boolean isCancelled = false;
            if (loggedInPatient != null) {
                isCancelled = cancelAppointment(loggedInPatient);
            }
            if (isCancelled){
                showAlert("Appointment Cancelled", "Your appointment has been cancelled");
            } else
                showAlert("Cancellation failed", "No appointments found for the given user");

            showPatientScene();
        } );

//        ------------------------------- CANCEL APPOINTMENT END -------------------------------------------------------
        Button backtohomeButton = new Button("Go Back");
        backtohomeButton.setOnAction(e -> {
            showLoginScene();
        } );
        VBox patienVbox = new VBox(10);
        patienVbox.setPadding(new Insets(20));
        patienVbox.getChildren().addAll(bookAppointmentButton, rescheduleAppointmentButton, cancelAppointmentButton,backtohomeButton);

        patientScene =new Scene(patienVbox, 400, 200);




//        ---------------------------------- ADD PATIENT STARTS -------------------------------------------------------
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameTextField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameTextField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneTextField = new TextField();


        Button saveNewPatientButton = new Button("Save Patient");
        saveNewPatientButton.setOnAction(e -> {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();

            savePatientDetails(firstName, lastName, email, phone);

            showAlert("Patient Added", "Patient details have been saved for: " + firstName + " " + lastName);
            clearFormFields(firstNameTextField, lastNameTextField, emailTextField, phoneTextField);
        });
        Button backtomanageButton = new Button("Go Back");
        backtomanageButton.setOnAction(e -> {
            showManagementScene();
        } );

        VBox addPatientVBox = new VBox(10);
        addPatientVBox.setPadding(new Insets(20));
        addPatientVBox.getChildren().addAll(firstNameLabel,firstNameTextField, lastNameLabel,lastNameTextField, emailLabel,emailTextField, phoneLabel,phoneTextField,saveNewPatientButton, backtomanageButton);

        BorderPane addPatientBorderPane = new BorderPane();
        addPatientBorderPane.setCenter(addPatientVBox);
        addPatientScene = new Scene(addPatientBorderPane, 400, 400);


        Button addPatientButton = new Button("Add Patient");
        addPatientButton.setOnAction(e -> {
            showAddPatientScene();
        } );

//        ------------------------------------ ADD PATIENT ENDS -------------------------------------------------



//        ------------------------------- REMOVE PATIENT START -------------------------------------------------------

        Button removePatientButton = new Button("Remove patient");
        removePatientButton.setOnAction(e -> {
            initializeRemovePatientScene();
            showRemovePatientScene();
        } );

//        ------------------------------- REMOVE PATIENT END -------------------------------------------------------

//        ------------------------------- FIND PATIENT START -------------------------------------------------------

        Button findPatientButton = new Button("Find Patient");
        findPatientButton.setOnAction(e -> {
            initializeFindPatientScene();
            showFindPatientScene();
        } );
//        ------------------------------- FIND PATIENT END -------------------------------------------------------


//        ------------------------------MANAGEMENT LOGIN SCENE SET -------------------------------------------------------

        Button backtologinButton = new Button("Go Back");
        backtologinButton.setOnAction(e -> {
            showLoginScene();
        } );

        VBox managementVbox = new VBox(10);
        managementVbox.setPadding(new Insets(20));
        managementVbox.getChildren().addAll(addPatientButton,removePatientButton,findPatientButton, backtologinButton);

        managementScene =new Scene(managementVbox, 400, 300);

    }
//        -------------------------------------------------------------------------------------------------------------



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void showCreateAccountScene() {
        primaryStage.setScene(createAccountScene);
    }

    private void showLoginScene() {
        primaryStage.setScene(loginScene);
    }

    private void showPatientScene(){
        primaryStage.setScene(patientScene);
    }

    private void showManagementScene(){ primaryStage.setScene(managementScene);}

    private void showBookAppointmentScene(){ primaryStage.setScene(bookAppointmentScene);}

    private void showAddPatientScene(){primaryStage.setScene(addPatientScene);}

    private void saveAppointmentDetails(String patientName, String date, String time) {
        appointmentsList.add(new Appointment(patientName,date,time));
        String fileName = patientName + "appointments.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("Date: " + date);
            bw.newLine();
            bw.write("Time: " + time);
            bw.newLine();
            bw.write("Name: " + patientName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean cancelAppointment(String patientName) {
        try {
            String fileName = patientName + "appointments.txt";
            File appointmentFile = new File(fileName);

            if (appointmentFile.exists()) {
                appointmentFile.delete();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void savePatientDetails(String firstName, String lastName, String email, String phone) {
        addPatientToList(firstName, lastName, email, phone);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("patients.txt", true))) {
            bw.write("First Name: " + firstName);
            bw.newLine();
            bw.write("Last Name: " + lastName);
            bw.newLine();
            bw.write("Email: " + email);
            bw.newLine();
            bw.write("Phone Number: " + phone);
            bw.newLine();
            bw.newLine(); // Separate the patient records with an empty line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFormFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.clear();
        }
    }


//        ------------------------------- REMOVE PATIENT METHODS -------------------------------------------------------

    private Scene removePatientScene;

    private void showRemovePatientScene() {
        primaryStage.setScene(removePatientScene);
    }

    private void initializeRemovePatientScene() {
        GridPane removePatientGridPane = new GridPane();
        removePatientGridPane.setPadding(new Insets(20));
        removePatientGridPane.setHgap(10);
        removePatientGridPane.setVgap(10);

        Label patientNameLabel = new Label("Patient Name:");
        TextField patientNameTextField = new TextField();

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            String patientName = patientNameTextField.getText();
            if (Patientmethods.removePatient(patientName)) {
                showAlert("Patient Removed", "Patient " + patientName + " has been removed.");
                clearFormFields(patientNameTextField);
            } else {
                showAlert("Patient Not Found", "No patient with the given name was found.");
            }
        });
        Button backtomanageButton = new Button("Go Back");
        backtomanageButton.setOnAction(e -> {
            showManagementScene();
        } );

        removePatientGridPane.add(patientNameLabel, 0, 0);
        removePatientGridPane.add(patientNameTextField, 1, 0);
        removePatientGridPane.add(removeButton, 1, 1);
        removePatientGridPane.add(backtomanageButton, 1,2);

        removePatientScene = new Scene(removePatientGridPane, 400, 150);
    }



//        ------------------------------- FIND PATIENT METHODS -------------------------------------------------------

    private Scene findPatientScene;

    private void showFindPatientScene() {
        primaryStage.setScene(findPatientScene);
    }

    private void initializeFindPatientScene() {
        GridPane findPatientGridPane = new GridPane();
        findPatientGridPane.setPadding(new Insets(20));
        findPatientGridPane.setHgap(10);
        findPatientGridPane.setVgap(10);

        Label patientNameLabel = new Label("Patient Name:");
        TextField patientNameTextField = new TextField();

        Button findButton = new Button("Find");
        findButton.setOnAction(e -> {
            String patientName = patientNameTextField.getText();
            String patientDetails = Patientmethods.findPatient(patientName);
            if (patientDetails != null) {
                showAlert("Patient Found", "Patient details:\n" + patientDetails);
                clearFormFields(patientNameTextField);
            } else {
                showAlert("Patient Not Found", "No patient with the given name was found.");
            }
        });

        Button backtomanageButton = new Button("Go Back");
        backtomanageButton.setOnAction(e -> {
            showManagementScene();
        } );


        findPatientGridPane.add(patientNameLabel, 0, 0);
        findPatientGridPane.add(patientNameTextField, 1, 0);
        findPatientGridPane.add(findButton, 1, 1);
        findPatientGridPane.add(backtomanageButton, 1, 2);

        findPatientScene = new Scene(findPatientGridPane, 400, 200);
    }
    private boolean removePatientFromList(String patientName) {
        Patient patientToRemove = null;
        for (Patient patient : patientsList) {
            if (patient.getFirstName().equals(patientName)) {
                patientToRemove = patient;
                break;
            }
        }

        if (patientToRemove != null) {
            patientsList.remove(patientToRemove);
            return true;
        }
        return false;
    }





}

