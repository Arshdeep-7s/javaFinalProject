package com.example.project1;

import java.io.*;
import java.util.ArrayList;

public class Patientmethods {
    public static String findPatient(String patientName) {
        ArrayList<String> patientDetails = new ArrayList<>();
        try {
            String fileName = "patients.txt";
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line;
            boolean patientFound = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("First Name: " + patientName)) {
                    patientDetails.add(line);
                    for (int i = 0; i < 3; i++) {
                        line = br.readLine();
                        patientDetails.add(line);
                    }
                    patientFound = true;
                    break;
                }
            }

            br.close();

            return patientFound ? String.join("\n", patientDetails) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean removePatient(String patientName) {
        ArrayList<String> newPatientData = new ArrayList<>();
        try {
            String fileName = "patients.txt";
            File patientsFile = new File(fileName);

            BufferedReader br = new BufferedReader(new FileReader(patientsFile));

            String line;
            boolean patientRemoved = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("First Name: " + patientName)) {
                    // Skip this patient's data (effectively removing it)
                    for (int i = 0; i < 4; i++) {
                        br.readLine();
                    }
                    patientRemoved = true;
                    continue;
                }
                newPatientData.add(line);
            }

            br.close();

            if (patientRemoved) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(patientsFile))) {
                    for (String newDataLine : newPatientData) {
                        bw.write(newDataLine);
                        bw.newLine();
                    }
                }
            }

            return patientRemoved;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
