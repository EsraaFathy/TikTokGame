package tictokserver;


public class HandlingMessage {
private String arr[];
    public HandlingMessage(String message) {
        
        arr=message.split("##");

		for (int i=0; i < arr.length; i ++){
			System.out.println(arr[i]);
		}
    }
    
    
    public String[] dataArray(){
    return arr;
    }
}
