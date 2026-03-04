/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Raven Mancao
 */

/*inuna ko muna yung customer para sa info 
and nag-iisip ng idea sa tatlo ko pang class (carInfo(vehicles),rentalForm(rentaltrans),maintenance(sa vehicles)
NOTE!!!
*/



import java.util.ArrayList; //ArrayList dahil ArrayList lang naman ang ginamit ko util

//main class
public class CarRentalSys {
    //list ng lahat ng customers
private ArrayList<Customer>customers = new ArrayList<>();

//method para sa magdadagdag ng bagong customer
public void addCustomer(Customer customer){
    customers.add(customer);
}

//method para sa hahanapin na customer gamit lamang ang ID
public Customer searchCustomer(int id){
    
    //che-check bawat customer sa list
    for(Customer c:customers){
        //dito naman ay kung kaparehas ba ang hinahanap na ID
        if (c.getCustomerId()==id){
            return c;
    }
    }
    //null kung walang mahanap or ibabalik
    return null;
}
//method para mag delete ng customer
public void deleteCustomer(Customer customer){
    customers.remove(customer);//tinanggal sa list ang customer
}
}

