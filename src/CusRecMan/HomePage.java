package CusRecMan;

import javax.swing.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Customer Record Management");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCustomer = new JButton("WELCOME ADMIN, CLICK ME!");
        btnCustomer.setBounds(180, 220, 230, 50);
        add(btnCustomer);

        btnCustomer.addActionListener(e -> {
            CustomerForm cf = new CustomerForm();
            cf.setVisible(true);
            this.dispose();
        });
    }
}