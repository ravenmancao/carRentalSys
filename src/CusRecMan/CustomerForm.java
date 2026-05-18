package CusRecMan;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomerForm extends JFrame implements ActionListener, Searchable {

    private ArrayList<CustomerPage> customerList = new ArrayList<>();

    private JTextField txtId, txtName, txtPhone, txtLicense, txtAddress, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch, btnView;

    public CustomerForm() {

        setTitle("Customer Management");
        setSize(600, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("ID")).setBounds(70, 90, 100, 25);
        txtId = new JTextField();
        txtId.setBounds(170, 90, 150, 25);
        add(txtId);

        add(new JLabel("Name")).setBounds(70, 120, 100, 25);
        txtName = new JTextField();
        txtName.setBounds(170, 120, 150, 25);
        add(txtName);

        add(new JLabel("Phone")).setBounds(70, 150, 100, 25);
        txtPhone = new JTextField();
        txtPhone.setBounds(170, 150, 150, 25);
        add(txtPhone);

        add(new JLabel("License")).setBounds(70, 180, 100, 25);
        txtLicense = new JTextField();
        txtLicense.setBounds(170, 180, 150, 25);
        add(txtLicense);

        add(new JLabel("Address")).setBounds(70, 210, 100, 25);
        txtAddress = new JTextField();
        txtAddress.setBounds(170, 210, 150, 25);
        add(txtAddress);

        txtSearch = new JTextField();
        txtSearch.setBounds(70, 20, 200, 25);
        add(txtSearch);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(340, 90, 100, 25);
        add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(340, 120, 100, 25);
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(340, 150, 100, 25);
        add(btnDelete);

        btnClear = new JButton("Clear");
        btnClear.setBounds(340, 180, 100, 25);
        add(btnClear);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(290, 20, 100, 25);
        add(btnSearch);

        btnView = new JButton("View");
        btnView.setBounds(400, 20, 100, 25);
        add(btnView);

        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);
        btnSearch.addActionListener(this);
        btnView.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {

            if (!validateFields()) return;

            int id;
            try {
                id = Integer.parseInt(txtId.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID! Numbers only.");
                return;
            }

            for (CustomerPage c : customerList) {
                if (c.getId() == id) {
                    JOptionPane.showMessageDialog(this, "ID already exists!");
                    return;
                }
            }

            customerList.add(new CustomerPage(
                    id,
                    txtName.getText().trim(),
                    txtPhone.getText().trim(),
                    txtLicense.getText().trim(),
                    txtAddress.getText().trim()
            ));

            JOptionPane.showMessageDialog(this, "Customer Added!");
            clearFields();
        }

        else if (e.getSource() == btnUpdate) {

            int id;
            try {
                id = Integer.parseInt(txtId.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID! Numbers only.");
                return;
            }

            for (CustomerPage c : customerList) {
                if (c.getId() == id) {

                    c.setName(txtName.getText().trim());
                    c.setPhone(txtPhone.getText().trim());
                    c.setLicense(txtLicense.getText().trim());
                    c.setAddress(txtAddress.getText().trim());

                    JOptionPane.showMessageDialog(this, "Updated!");
                    clearFields();
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Not found");
        }

        else if (e.getSource() == btnDelete) {

            int id;
            try {
                id = Integer.parseInt(txtId.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID! Numbers only.");
                return;
            }

            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getId() == id) {
                    customerList.remove(i);
                    JOptionPane.showMessageDialog(this, "Deleted!");
                    clearFields();
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Not found");
        }

        else if (e.getSource() == btnSearch) {
            search(txtSearch.getText());
        }

        else if (e.getSource() == btnView) {

            String keyword = txtSearch.getText().trim();

            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter ID or Name first");
                return;
            }

            ArrayList<CustomerPage> results = searchResults(keyword);

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No customer found");
            }
            else if (results.size() == 1) {
                showDetails(results.get(0));
            }
            else {

                String[] options = new String[results.size()];

                for (int i = 0; i < results.size(); i++) {
                    options[i] = results.get(i).getId() + " - " + results.get(i).getName();
                }

                String choice = (String) JOptionPane.showInputDialog(
                        this,
                        "Multiple customers found:",
                        "Select Customer",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice != null) {
                    int id = Integer.parseInt(choice.split(" - ")[0]);

                    for (CustomerPage c : results) {
                        if (c.getId() == id) {
                            showDetails(c);
                            break;
                        }
                    }
                }
            }
        }

        else if (e.getSource() == btnClear) {
            clearFields();
        }
    }

    @Override
    public void search(String keyword) {

        String result = "";

        for (CustomerPage c : customerList) {
            if (String.valueOf(c.getId()).contains(keyword) ||
                c.getName().toLowerCase().contains(keyword.toLowerCase())) {

                result += c.getId() + " - " + c.getName() + "\n";
            }
        }

        JOptionPane.showMessageDialog(this,
                result.isEmpty() ? "No Result" : result);
    }

    private ArrayList<CustomerPage> searchResults(String keyword) {

        ArrayList<CustomerPage> results = new ArrayList<>();

        for (CustomerPage c : customerList) {
            if (String.valueOf(c.getId()).contains(keyword) ||
                c.getName().toLowerCase().contains(keyword.toLowerCase())) {

                results.add(c);
            }
        }

        return results;
    }

    private boolean validateFields() {

        if (txtId.getText().trim().isEmpty() ||
            txtName.getText().trim().isEmpty() ||
            txtPhone.getText().trim().isEmpty() ||
            txtLicense.getText().trim().isEmpty() ||
            txtAddress.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please complete all fields!");
            return false;
        }

        if (!txtName.getText().trim().matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Name must contain letters only!");
            return false;
        }

        if (!txtPhone.getText().trim().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone must be numbers only!");
            return false;
        }

        return true;
    }

    private void showDetails(CustomerPage c) {

        JOptionPane.showMessageDialog(this,
                "ID: " + c.getId() +
                "\nName: " + c.getName() +
                "\nPhone: " + c.getPhone() +
                "\nLicense: " + c.getLicense() +
                "\nAddress: " + c.getAddress(),
                "Customer Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtLicense.setText("");
        txtAddress.setText("");
        txtSearch.setText("");
    }

    public static void main(String[] args) {
        new CustomerForm().setVisible(true);
    }
}