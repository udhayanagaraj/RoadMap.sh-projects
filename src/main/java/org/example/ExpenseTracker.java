package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class ExpenseTracker {
    static Path filePath = null;

    static void add(String[] args){
        String description = "";
        String amount = "";

        for(int i=1;i<args.length;i++){
            if(args[i].equals("--description")){
                description = args[i+1];
                i++;
            } else if (args[i].equals("--amount")) {
                amount = args[i+1];
                i++;
            }
        }
        if(description.isEmpty() && amount.isEmpty()){
            System.out.println("Error: Missing the description and amount");
            System.exit(1);
        }
        int id = getLastIndex(filePath) + 1;
        String s = id +"  "+LocalDate.now().toString()+"  "+ description+"  "+ amount;
        addTextToFile(filePath,s);
        System.out.println("Expense added successfully");
    }

    static void list(){
        System.out.println("Id  Date      Description  Amount");
        readFileContent(filePath);
    }

    static void summary(){
        int total = getSummary(filePath);
        System.out.println("Total Expenses: "+total);
    }

    static void summaryByMonth(String[] args){
        int total = monthWiseSummary(filePath,args[2]);
        System.out.println("Total Expenses for the month "+args[2]+": "+total);
    }

    static void delete(String[]args){
        deleteLine(filePath,args[2]);
    }

    static void createFileIfNotExists(Path filePath){
        try{
            if(!Files.exists(filePath)){
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    static void addTextToFile(Path filePath,String text){
        try {
            Files.write(filePath,(text + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void readFileContent(Path filePath){
        try {
            List<String> fileContent = Files.readAllLines(filePath);
            if(fileContent.isEmpty()){
                System.out.println("File is empty");
            }else{
                for (String content : fileContent){
                    System.out.println(content);
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

    }

    static int getLastIndex(Path filePath){
        int id = 0;
        try{
            List<String> file = Files.readAllLines(filePath);

            if(file.isEmpty()){
                return id;
            }else{
                String lastLine = file.get(file.size() -1);
                String[] contents = lastLine.split(" ");
                try{
                    id = Integer.parseInt(contents[0]);
                }catch (NumberFormatException e){
                    System.err.println("Error: Invalid id in the file");
                }
            }

            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static int getSummary(Path filePath){
        int value = 0;
        try{
            List<String> fileContent = Files.readAllLines(filePath);
            if(!fileContent.isEmpty()){
               for(String contents : fileContent){
                   String[] content = contents.split(" ");
                   value += Integer.parseInt(content[6]);
               }
            }else{
                System.err.println("Error: File is empty");
                System.exit(1);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return value;
    }

    static int monthWiseSummary(Path filePath,String month){
        int total = 0;
        try{
            List<String> fileContent = Files.readAllLines(filePath);

            if(!fileContent.isEmpty()){
                for(String contents : fileContent) {
                    String[] content = contents.split(" ");
                    String[] date = content[2].split("-");
                    if(month.equals(date[1])){
                        total += Integer.parseInt(content[6]);
                    }
                }
            }else{
                return total;
            }
            return total;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void deleteLine(Path filePath,String id){
        try{
            List<String> fileContents = Files.readAllLines(filePath);

            List<String> updateContent = new ArrayList<>();

            boolean found = false;
            for(String line : fileContents){
                String[] contents = line.split(" ");
                if(contents.length > 4 && contents[0].equals(id)){
                    found = true;
                    continue;
                }
                updateContent.add(line);
            }

            if(found){
                Files.write(filePath,updateContent,StandardCharsets.UTF_8);
                System.out.println("Expense with id: "+id+" deleted successfully");
            }else{
                System.out.println("Expense with id: "+id +" not found");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {


        if (args.length < 1) {
            System.out.println("Enter a valid argument");
            System.exit(1);
        }

        filePath = Paths.get("expenses.txt");

        createFileIfNotExists(filePath);

        String command = args[0];

        switch (command) {
            case "add":
                add(args);
                break;
            case "list":
                list();
                break;
            case "summary":
                if(args.length > 1 && args[1].equals("submit") && args.length == 3){
                    summaryByMonth(args);

                }else {
                    summary();
                }
                break;
            case "delete":
                delete(args);
            default:
                System.out.println("Examples");
                System.out.println("add --description 'Breakfast' --amount 100");
                System.out.println("list");
                System.out.println("summary");
                System.out.println("delete --id 1");
                System.out.println("summary --month 8");
                break;
        }
    }

    //add --description "Breakfast" --amount 100
    //
}
