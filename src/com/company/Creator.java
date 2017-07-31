package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import java.io.*;


public class Creator {
    public void createWorkOrders() {
        // read input, create work orders and assign id, and write as json files

        ObjectMapper mapper = new ObjectMapper();

        Scanner scanner = new Scanner(System.in);
        WorkOrder workOrder1 = new WorkOrder();

        System.out.println("What is the Work Order ID:");
        workOrder1.setId(scanner.nextInt());
        scanner.nextLine();

        System.out.println("What is the Description for the Work Order?");
        workOrder1.setDescription(scanner.nextLine());

        System.out.println("Who created the Work Order?");
        workOrder1.setSenderName(scanner.nextLine());

        workOrder1.setStatus(Status.INITIAL);

        String json = null;
        File newWorkOrder = new File(workOrder1.getId() + ".json");

        try {
            json = mapper.writeValueAsString(workOrder1);
            System.out.println("workOrder1: " + workOrder1);
            System.out.println("json: " + json);
            FileWriter createFile = new FileWriter(newWorkOrder);
            createFile.write(json);
            createFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}
