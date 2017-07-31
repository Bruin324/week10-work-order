package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.io.*;

import static com.company.Status.*;

public class Processor {
    ObjectMapper mapper = new ObjectMapper();
    Set<WorkOrder> initialSet = new HashSet<>();
    Set<WorkOrder> assignedSet = new HashSet<>();
    Set<WorkOrder> inProgressSet = new HashSet<>();
    Set<WorkOrder> doneSet = new HashSet<>();
    Map<Status, Set<WorkOrder>> workOrderMap = new HashMap();



    public void processWorkOrders() {

        readIt();
        moveIt();
    }

    private void moveIt() {
        initialSet = workOrderMap.get(INITIAL);
        System.out.println("Line 28 initialSet: " + initialSet);
//        assignedSet = workOrderMap.get(ASSIGNED);
        System.out.println("Line 30 assignedSet: " + assignedSet);
//        inProgressSet = workOrderMap.get(IN_PROGRESS);
        System.out.println("Line 32 inProgressSet: " + inProgressSet);
//        doneSet = workOrderMap.get(DONE);
        System.out.println("Line 34 doneSet: " + doneSet);

//        for(WorkOrder order : doneSet) {
//            order.setStatus(Status.DONE);
//            String json;
//            try {
//                File newWorkOrder = new File(order.getId() + ".json");
//                json = mapper.writeValueAsString(order);
//                FileWriter createFile = new FileWriter(newWorkOrder);
//                createFile.write(json);
//                createFile.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        doneSet.clear();

        for(WorkOrder order : inProgressSet) {
            doneSet.add(order);
            System.out.println("moving " + order.getId() + " from inProgress to Done");
        }
        inProgressSet.clear();

        for(WorkOrder order : assignedSet) {
            inProgressSet.add(order);
            System.out.println("moving " + order.getId() + " from assigned to inProgress");
        }
        assignedSet.clear();

        for(WorkOrder order : initialSet) {
            assignedSet.add(order);
            System.out.println("moving " + order.getId() + " from initial to assigned");
        }
        initialSet.clear();
    }

    private void readIt() {
        // read the json files into WorkOrders and put in map
        File currentDirectory = new File(".");
        File files[] = currentDirectory.listFiles();
        Set<WorkOrder> initialSet = new HashSet<>();


        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                try (FileReader jsonFiles = new FileReader(f)){
                    WorkOrder wo = mapper.readValue(jsonFiles, WorkOrder.class);
                    initialSet.add(wo);
                    workOrderMap.put(INITIAL, initialSet);
                    System.out.println("Received new Work Order: " + wo);
                    f.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        Processor processor = new Processor();
        try {
            while(true){
                processor.processWorkOrders();
                Thread.sleep(5000l);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
