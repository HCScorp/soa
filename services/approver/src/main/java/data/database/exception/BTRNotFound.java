package data.database.exception;

public class BTRNotFound extends Exception {
    private int id;

    public BTRNotFound(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }


    public String cause(){
        return "Business Travel Request #" + id + " not found in database";
    }
}