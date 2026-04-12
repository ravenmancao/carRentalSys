package CusRecMan;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomerForm extends JFrame implements Searchable {
    private ArrayList<CustomerPage> customerList = new ArrayList<>();

    private JTextField txtId, txtName, txtPhone, txtLicense, txtAddress, txtSearch;

    public CustomerForm() {
        setTitle("Customer Management");
        setSize(600, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("Customer ID:");
        lblId.setBounds(50, 70, 100, 25);
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(150, 70, 150, 25);
        add(txtId);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 100, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(150, 100, 150, 25);
        add(txtName);

        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(50, 130, 100, 25);
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(150, 130, 150, 25);
        add(txtPhone);

        JLabel lblLicense = new JLabel("Drivers License:");
        lblLicense.setBounds(50, 160, 100, 25);
        add(lblLicense);
        txtLicense = new JTextField();
        txtLicense.setBounds(150, 160, 150, 25);
        add(txtLicense);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(50, 190, 100, 25);
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(150, 190, 150, 25);
        add(txtAddress);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(320, 70, 100, 25);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(320, 100, 100, 25);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(320, 130, 100, 25);
        add(btnDelete);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(320, 160, 100, 25);
        add(btnClear);

        txtSearch = new JTextField();
        txtSearch.setBounds(50, 20, 200, 25);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(270, 20, 100, 25);
        add(btnSearch);

        JButton btnView = new JButton("View");
        btnView.setBounds(380, 20, 100, 25);
        add(btnView);

        JButton btnBack = new JButton("Back to Main Menu");
        btnBack.setBounds(200, 280, 180, 40);
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
           StringBuilder missing = new StringBuilder();

            if(txtName.getText().isEmpty()) missing.append("Fullname, ");
            if(txtPhone.getText().isEmpty()) missing.append("Phone Number, ");
            if(txtLicense.getText().isEmpty()) missing.append("Driver's License, ");
            if(txtAddress.getText().isEmpty()) missing.append("Address, ");

            if(missing.length() > 0){
                JOptionPane.showMessageDialog(this,"Missing fields: " + missing.substring(0, missing.length() - 2));
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
                txtAddress.getText()            
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
                JOptionPane.showMessageDialog(this, "Enter ID or Fullname to search.");
                return;
            }
            search(keyword);
        });

        btnView.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if(keyword.isEmpty()){
                JOptionPane.showMessageDialog(this, "Enter ID or Fullname to search before viewing.");
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
        txtSearch.setText("");
    }

    private void showCustomerDetails(CustomerPage c){
        JOptionPane.showMessageDialog(this,
            "ID: " + c.getId() +
            "\nName: " + c.getName() +
            "\nPhone: " + c.getPhone() +
            "\nLicense: " + c.getLicense() +
            "\nAddress: " + c.getAddress(),
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