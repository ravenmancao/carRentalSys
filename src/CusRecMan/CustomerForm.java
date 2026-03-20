package CusRecMan;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomerForm extends JFrame implements Searchable {
    private ArrayList<CustomerPage> customerList = new ArrayList<>();

    private JTextField txtId, txtName, txtPhone, txtLicense, txtAddress, txtCar, txtRentalDate, txtReturnDate, txtSearch;

    public CustomerForm() {
        setTitle("Customer Record Management");
        setSize(600, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("Customer ID:");
        lblId.setBounds(50, 20, 100, 25);
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(150, 20, 150, 25);
        add(txtId);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 50, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(150, 50, 150, 25);
        add(txtName);

        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(50, 80, 100, 25);
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(150, 80, 150, 25);
        add(txtPhone);

        JLabel lblLicense = new JLabel("Drivers License:");
        lblLicense.setBounds(50, 110, 100, 25);
        add(lblLicense);
        txtLicense = new JTextField();
        txtLicense.setBounds(150, 110, 150, 25);
        add(txtLicense);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(50, 140, 100, 25);
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(150, 140, 150, 25);
        add(txtAddress);
        
        JLabel lblCar = new JLabel("Rented Car:");
        lblCar.setBounds(50, 170, 100, 25);
        add(lblCar);
        txtCar = new JTextField();
        txtCar.setBounds(150, 170, 150, 25);
        add(txtCar);
        
        JLabel lblRental = new JLabel("Rental Date:");
        lblRental.setBounds(50, 200, 100, 25);
        add(lblRental);
        txtRentalDate = new JTextField();
        txtRentalDate.setBounds(150, 200, 150, 25);
        add(txtRentalDate);
        
        JLabel lblReturn = new JLabel("Return Date:");
        lblReturn.setBounds(50, 230, 100, 25);
        add(lblReturn);
        txtReturnDate = new JTextField();
        txtReturnDate.setBounds(150, 230, 150, 25);
        add(txtReturnDate);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(320, 20, 100, 25);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(320, 50, 100, 25);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(320, 80, 100, 25);
        add(btnDelete);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(320, 110, 100, 25);
        add(btnClear);

        txtSearch = new JTextField();
        txtSearch.setBounds(50, 265, 200, 25);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(270, 265, 100, 25);
        add(btnSearch);

        JButton btnView = new JButton("View");
        btnView.setBounds(380, 265, 100, 25);
        add(btnView);

        JButton btnBack = new JButton("Back to Main Menu");
        btnBack.setBounds(200, 300, 200, 30);
        add(btnBack);

        btnAdd.addActionListener(e -> {
            if(txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID cannot be empty.");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(txtId.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be a number.");
                return;
            }
            for(CustomerPage c : customerList){
                if(c.getId() == id){
                    JOptionPane.showMessageDialog(this, "Customer with this ID already exists.");
                    return;
                }
            }
            CustomerPage c = new CustomerPage(
                id,
                txtName.getText(),
                txtPhone.getText(),
                txtLicense.getText(),
                txtAddress.getText(),
                txtCar.getText(),
                txtRentalDate.getText(),
                txtReturnDate.getText()               
            );
            customerList.add(c);
            JOptionPane.showMessageDialog(this, "Customer added.");
            clearFields();
        });

        btnUpdate.addActionListener(e -> {
            if(txtId.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "ID is required to update.");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(txtId.getText());
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "ID must be a number.");
                return;
            }
            CustomerPage found = null;
            for(CustomerPage c : customerList){
                if(c.getId() == id){
                    found = c;
                    break;
                }
            }
            if(found == null){
                JOptionPane.showMessageDialog(this, "Customer not found.");
                return;
            }
            found.setName(txtName.getText());
            found.setPhone(txtPhone.getText());
            found.setLicense(txtLicense.getText());
            found.setAddress(txtAddress.getText());
            found.setCar(txtCar.getText());
            found.setRentalDate(txtRentalDate.getText());
            found.setReturnDate(txtReturnDate.getText());
            JOptionPane.showMessageDialog(this, "Customer updated.");
            clearFields();
        });

        btnDelete.addActionListener(e -> {
            if(txtId.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "ID is required to delete.");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(txtId.getText());
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "ID must be a number.");
                return;
            }
            boolean removed = customerList.removeIf(c -> c.getId() == id);
            if(removed){
                JOptionPane.showMessageDialog(this, "Customer deleted.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found.");
            }
        });

        btnClear.addActionListener(e -> clearFields());

        btnBack.addActionListener(e -> {
            HomePage hp = new HomePage();
            hp.setVisible(true);
            this.dispose();
        });

        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if(keyword.isEmpty()){
                JOptionPane.showMessageDialog(this, "Enter ID or FullName to search.");
                return;
            }
            search(keyword);
        });

        btnView.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if(keyword.isEmpty()){
                JOptionPane.showMessageDialog(this, "Enter ID or Full Name to search before viewing.");
                return;
            }
            ArrayList<CustomerPage> results = searchResults(keyword);
            if(results.isEmpty()){
                JOptionPane.showMessageDialog(this, "No customer found.");
            } else if(results.size() == 1){
                CustomerPage c = results.get(0);
                showCustomerDetails(c);
            } else {
                String[] options = new String[results.size()];
                for(int i=0; i<results.size(); i++){
                    options[i] = results.get(i).getId() + " - " + results.get(i).getName();
                }
                String choice = (String) JOptionPane.showInputDialog(this, 
                    "Multiple customers found. Select one:", "Select Customer",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if(choice != null){
                    int chosenId = Integer.parseInt(choice.split(" - ")[0]);
                    CustomerPage chosen = null;
                    for(CustomerPage c : results){
                        if(c.getId() == chosenId){
                            chosen = c;
                            break;
                        }
                    }
                    if(chosen != null){
                        showCustomerDetails(chosen);
                    }
                }
            }
        });
    }

    private void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtLicense.setText("");
        txtAddress.setText("");
        txtCar.setText("");
        txtRentalDate.setText("");
        txtReturnDate.setText("");
        txtSearch.setText("");
    }

    private void showCustomerDetails(CustomerPage c){
        JOptionPane.showMessageDialog(this,
            "ID: " + c.getId() +
            "\nName: " + c.getName() +
            "\nPhone: " + c.getPhone() +
            "\nLicense: " + c.getLicense() +
            "\nAddress: " + c.getAddress() +
             "\nRented Car: " + c.getCar() +
             "\nRental Date: " + c.getRentalDate() +
             "\nReturn Date: " + c.getReturnDate(),
            "Customer Details", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void search(String searchTerm){
    ArrayList<CustomerPage> matchedCustomers = searchResults(searchTerm);
    
    if(matchedCustomers.isEmpty()){
        JOptionPane.showMessageDialog(this, "No customer found.");
    } else {
        StringBuilder message = new StringBuilder("Found " + matchedCustomers.size() + " customer(s):\n");
        
        for(CustomerPage customer : matchedCustomers){
            message.append(customer.getId())
                   .append(" - ")
                   .append(customer.getName())
                   .append("\n");
        }
        
        JOptionPane.showMessageDialog(this, message.toString());
    }
}

    private ArrayList<CustomerPage> searchResults(String keyword){
        ArrayList<CustomerPage> results = new ArrayList<>();
        for(CustomerPage c : customerList){
            if(String.valueOf(c.getId()).equals(keyword) || c.getName().toLowerCase().contains(keyword.toLowerCase())){
                results.add(c);
            }
        }
        return results;
    }
}