package view;

import classes.Funcionario;
import controller.service.AddressService;
import controller.service.EmployeeService;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Address;
import model.Employee;

public class CreateUpdateEmployee extends javax.swing.JFrame {
    
    // FIELDS
    
    private Employee employee = null;
    private AddressService addressService = new AddressService();

    // FUNCTIONS
    
    private void setComboLists () {
        
        try {
            String[] roles = {
                "Select a role",
                "Demartologist",
                "General Practitioner",
                "Neurologist",
                "Ophthalmologist",
                "Operator",
                "Orthopedist",
                "Pediatrician"
            };

            comboRole.removeAllItems();
            for (String role : roles) {
                comboRole.addItem(role);
            }

            comboAddress.removeAllItems();
            comboAddress.addItem("Select a address");
            List<Address> addresses = addressService.listAll();
            addresses.forEach (address -> {
                String content = "%1s : %2s, %3s, %4s - %5s - %6s / %7s / %8s";
                content = String.format(content, address.getId(), address.getStreet(), address.getNumber(), 
                                                 address.getComplement(), address.getZipCode(), address.getCity(), 
                                                 address.getState(), address.getCountry());
                comboAddress.addItem(content);
            });
        }
        catch (Exception ex) {
            System.out.println("CreateUpdateEmployee:54 = " + ex.getMessage());
        }
    }
    
    private void toggleAddressComponents (boolean flag) {
        addressPanel.setEnabled(flag);
        
        for (Component component : addressPanel.getComponents()) {
            component.setEnabled(flag);
        }
    }
    
    private void setComponentsDefaultValues () {
        
        this.setTitle("Employee");
        this.setResizable(true);
        comboAddress.setEnabled(false);
        checkAddress.setSelected(false);
        radioExisting.setSelected(false);
        radioExisting.setEnabled(false);
        radioNew.setSelected(false);
        radioNew.setEnabled(false);
        toggleAddressComponents(false);
        
        Font font = new Font("Monospaced", Font.BOLD, 16);
        
        JLabel[] labels = { 
            labelCity, labelComplement, labelLicenseNumber,
            labelName, labelNumber, labelRole, labelSocialSecurityNumber,
            labelState, labelStreet, labelZipCode, labelCountry, labelEmail,
            labelPhoneNumber
        };
        
        JComponent[] inputs = {
            inputCity, inputComplement, inputLicenseNumber, inputName, inputNumber, 
            comboRole, inputSocialSecurityNumber, inputState, inputStreet, inputZipCode,
            inputCountry, inputEmail, inputPhoneNumber
        };
        
        String[] labelsTexts = {
            "* City ", "Complement", "* License Number ", "* Name ", "Number",
            "* Role ", "* SSN ", "* State ", "* Street ", "* Zip Code ", "* Country",
            "* Email", "Phone Number"
        };
        
        for (int index = 0; index < labels.length; index++) {
            labels[index].setText(labelsTexts[index]);
            labels[index].setFont(font);
            inputs[index].setFont(font);
        }
    }
    
    private void validation (boolean isUpdate) {
        Set<String> requiredFields = new HashSet<>();
        
        if (inputLicenseNumber.getText().isEmpty()) {
            requiredFields.add(labelLicenseNumber.getText());
        }
        
        if (inputName.getText().isEmpty()) {
            requiredFields.add(labelName.getText());
        }
        
        if (comboRole.getSelectedIndex() == 0) {
            requiredFields.add(labelRole.getText());
        }
        
        if (inputSocialSecurityNumber.getText().isEmpty() || inputSocialSecurityNumber.getText().length() != 14) {
            requiredFields.add(labelSocialSecurityNumber.getText());
        }
        
        if (inputEmail.getText().isEmpty()) {
            requiredFields.add(labelEmail.getText());
        }
        
        if (checkAddress.isSelected() && radioExisting.isSelected()) {
            if (comboAddress.getSelectedIndex() == 0) {
                requiredFields.add("*Existing Address");
            }
        else if (checkAddress.isSelected() && radioNew.isSelected()) {
            if (inputZipCode.getText().isEmpty()) {
                requiredFields.add(labelZipCode.getText());
            }

            if (inputCity.getText().isEmpty()) {
                requiredFields.add(labelCity.getText());
            }

            if (inputState.getText().isEmpty()) {
                requiredFields.add(labelState.getText());
            }

            if (inputCountry.getText().isEmpty()) {
                requiredFields.add(labelCountry.getText());
            }
        }
    }
        
        if (requiredFields.isEmpty()) {
            save (isUpdate);
        }
        else {
            JOptionPane.showMessageDialog(this, requiredFields);
        }
    }
    
    private void save (boolean isUpdate) {
        
        try {
            Timestamp createdAt = (isUpdate ? employee.getCreatedAt() : new Timestamp(System.currentTimeMillis ()));
            String email = inputEmail.getText();
            Integer id = (isUpdate ? employee.getId() : 0);
            String licenseNumber = inputLicenseNumber.getText();
            String name = inputName.getText();
            String phoneNumber = inputPhoneNumber.getText();
            String role = comboRole.getItemAt(comboRole.getSelectedIndex());
            String socialSecurityNumber = inputSocialSecurityNumber.getText();
            Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
            boolean canSucceed = true;
            
            Address address = new Address();
            if (checkAddress.isSelected()) {
                if (radioExisting.isSelected()) {
                    String selectedValue = comboAddress.getItemAt(comboAddress.getSelectedIndex());
                    selectedValue = selectedValue.split(":")[0].trim();
                    address.setId(Integer.parseInt(selectedValue));
                }
                else if (radioNew.isSelected()) {
                    address.setId(isUpdate ? employee.getAddress().getId() : 0);
                    address.setCity(inputCity.getText());
                    address.setComplement(inputComplement.getText());
                    address.setCountry(inputCountry.getText());
                    address.setCreatedAt(isUpdate ? employee.getAddress().getCreatedAt(): new Timestamp(System.currentTimeMillis()));
                    address.setNumber(inputNumber.getText());
                    address.setState(inputState.getText());
                    address.setStreet(inputStreet.getText());
                    address.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    address.setZipCode(inputZipCode.getText());
                    
                    if (address.getId() != null) {
                        boolean success = (isUpdate ? addressService.update(address) : addressService.insert(address));
                        canSucceed = success;
                        if (success && !isUpdate) {
                            address.setId(addressService.getLastId());
                        }
                    }
                }
            }
            
            if (canSucceed) {
                System.out.println("AddressId: " + address.getId());
            
                employee = (employee != null ? employee : new Employee());
                employee.setId(id);
                employee.setCreatedAt(createdAt);
                employee.setEmail(email);
                employee.setLicenseNumber(licenseNumber);
                employee.setName(name);
                employee.setPhoneNumber(phoneNumber);
                employee.setRole(role);
                employee.setSocialSecurityNumber(socialSecurityNumber);
                employee.setUpdatedAt(updatedAt);
                employee.setAddress(address);

                EmployeeService service = new EmployeeService();
                boolean success = (isUpdate ? service.update(employee) : service.insert(employee));
                if (success) {
                    JOptionPane.showConfirmDialog(this, "Operation has succeed! Go to employers search?", "Success", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);
                }
                else {
                    JOptionPane.showMessageDialog(this, "This operation has failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Some operation failed on Address data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void setEventListeners () {
        checkAddress.addActionListener ((ev) -> {
            radioExisting.setEnabled(checkAddress.isSelected());
            radioNew.setEnabled(checkAddress.isSelected());
            radioGroup.setSelected(null, false);
            radioExisting.setSelected(false);
            radioNew.setSelected(false);
            toggleAddressComponents (false);
            comboAddress.setEnabled (false);
        });
        
        radioExisting.addActionListener((ev) -> {
            comboAddress.setEnabled(radioExisting.isSelected());
            toggleAddressComponents (false);
        });
        
        radioNew.addActionListener((ev) -> {
            toggleAddressComponents(radioNew.isSelected());
            comboAddress.setEnabled (false);
        });
        
        
        buttonSave.addActionListener ((ev) -> {
            System.out.println ("Clicked");
            validation(false);
            
        });
    }

    public CreateUpdateEmployee() {
        initComponents();
        setComponentsDefaultValues();
        setComboLists();
        setEventListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioGroup = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        labelSocialSecurityNumber = new javax.swing.JLabel();
        labelRole = new javax.swing.JLabel();
        labelLicenseNumber = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        labelPhoneNumber = new javax.swing.JLabel();
        inputName = new javax.swing.JTextField();
        inputLicenseNumber = new javax.swing.JTextField();
        inputEmail = new javax.swing.JTextField();
        inputPhoneNumber = new javax.swing.JTextField();
        inputSocialSecurityNumber = new javax.swing.JFormattedTextField();
        comboRole = new javax.swing.JComboBox<>();
        buttonSave = new javax.swing.JButton();
        addressPanel = new javax.swing.JPanel();
        labelCity = new javax.swing.JLabel();
        labelStreet = new javax.swing.JLabel();
        labelZipCode = new javax.swing.JLabel();
        labelNumber = new javax.swing.JLabel();
        labelState = new javax.swing.JLabel();
        labelComplement = new javax.swing.JLabel();
        labelCountry = new javax.swing.JLabel();
        inputCity = new javax.swing.JTextField();
        inputStreet = new javax.swing.JTextField();
        inputNumber = new javax.swing.JTextField();
        inputComplement = new javax.swing.JTextField();
        inputState = new javax.swing.JTextField();
        inputCountry = new javax.swing.JTextField();
        inputZipCode = new javax.swing.JFormattedTextField();
        checkAddress = new javax.swing.JCheckBox();
        radioExisting = new javax.swing.JRadioButton();
        radioNew = new javax.swing.JRadioButton();
        comboAddress = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee");
        setLocation(new java.awt.Point(490, 200));
        setName("frm_CADASTRAR"); // NOI18N

        mainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelName.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        labelName.setText("* Name:");
        labelName.setName("lbl_NOME"); // NOI18N

        labelSocialSecurityNumber.setText("* SSN:");
        labelSocialSecurityNumber.setName("lbl_CPF"); // NOI18N

        labelRole.setText("* Role:");
        labelRole.setName("lbl_CARGO"); // NOI18N

        labelLicenseNumber.setText("* License Number:");
        labelLicenseNumber.setName("lbl_CRM"); // NOI18N

        labelEmail.setText("* E-mail:");
        labelEmail.setName("lbl_CPF"); // NOI18N

        labelPhoneNumber.setText("Phone Number:");
        labelPhoneNumber.setName("lbl_CPF"); // NOI18N

        inputName.setName("txt_NOME"); // NOI18N

        inputLicenseNumber.setName("txt_CRM"); // NOI18N

        inputEmail.setName("txt_NOME"); // NOI18N

        inputPhoneNumber.setName("txt_NOME"); // NOI18N

        comboRole.setToolTipText("");
        comboRole.setName("cmb_CARGO"); // NOI18N

        buttonSave.setText("Save");
        buttonSave.setName("bnt_CADASTRAR"); // NOI18N

        addressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("New Address"));

        labelCity.setText("City:");
        labelCity.setName("lbl_CIDADE"); // NOI18N

        labelStreet.setText("Street");
        labelStreet.setName("lbl_BAIRRO"); // NOI18N

        labelZipCode.setText("Zip Code:");
        labelZipCode.setName("lbl_CEP"); // NOI18N

        labelNumber.setText("Number:");
        labelNumber.setName("lnl_NUM"); // NOI18N

        labelState.setText("State:");
        labelState.setName("lbl_UF"); // NOI18N

        labelComplement.setText("Complement:");
        labelComplement.setName("lbl_COMPLEMENTO"); // NOI18N

        labelCountry.setText("Country:");
        labelCountry.setName("lbl_UF"); // NOI18N

        inputCity.setName("txt_CIDADE"); // NOI18N

        inputStreet.setName("txt_BAIRRO"); // NOI18N

        inputNumber.setName("txt_NUM"); // NOI18N

        inputComplement.setName("txt_COMPLEMENTO"); // NOI18N

        inputZipCode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));

        javax.swing.GroupLayout addressPanelLayout = new javax.swing.GroupLayout(addressPanel);
        addressPanel.setLayout(addressPanelLayout);
        addressPanelLayout.setHorizontalGroup(
            addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addComponent(inputStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComplement))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(labelState))
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(inputCity, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputState, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelCity))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(labelCountry)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(inputCountry)))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(labelStreet)
                                .addGap(137, 137, 137)
                                .addComponent(labelNumber)
                                .addGap(76, 76, 76)
                                .addComponent(labelComplement))
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(labelZipCode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 148, Short.MAX_VALUE)))
                .addContainerGap())
        );
        addressPanelLayout.setVerticalGroup(
            addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelZipCode)
                    .addComponent(inputZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addComponent(labelStreet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNumber)
                            .addComponent(labelComplement))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComplement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addComponent(labelCountry)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelCity)
                            .addComponent(labelState))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        labelCity.getAccessibleContext().setAccessibleName("");

        checkAddress.setText("Address");

        radioGroup.add(radioExisting);
        radioExisting.setText("Existing");

        radioGroup.add(radioNew);
        radioNew.setText("New");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(comboAddress, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                    .addComponent(comboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(inputLicenseNumber))
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(labelRole))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(labelLicenseNumber)
                                                                .addComponent(labelSocialSecurityNumber))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE))
                                                        .addComponent(inputSocialSecurityNumber)))))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(labelName)))
                                    .addGap(12, 12, 12)
                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelEmail)
                                        .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelPhoneNumber)
                                        .addComponent(inputPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(checkAddress)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(radioExisting)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(radioNew)))))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonSave)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelName)
                    .addComponent(labelSocialSecurityNumber)
                    .addComponent(labelEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputSocialSecurityNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelRole)
                            .addComponent(labelLicenseNumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputLicenseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(labelPhoneNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkAddress)
                    .addComponent(radioExisting)
                    .addComponent(radioNew))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSave)
                .addGap(29, 29, 29))
        );

        labelSocialSecurityNumber.getAccessibleContext().setAccessibleName("");
        comboRole.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 355, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateUpdateEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateUpdateEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateUpdateEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateUpdateEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateUpdateEmployee().setVisible(true);
            }
        });
    }
    
    // COMPONENTS

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addressPanel;
    private javax.swing.JButton buttonSave;
    private javax.swing.JCheckBox checkAddress;
    private javax.swing.JComboBox<String> comboAddress;
    private javax.swing.JComboBox<String> comboRole;
    private javax.swing.JTextField inputCity;
    private javax.swing.JTextField inputComplement;
    private javax.swing.JTextField inputCountry;
    private javax.swing.JTextField inputEmail;
    private javax.swing.JTextField inputLicenseNumber;
    private javax.swing.JTextField inputName;
    private javax.swing.JTextField inputNumber;
    private javax.swing.JTextField inputPhoneNumber;
    private javax.swing.JFormattedTextField inputSocialSecurityNumber;
    private javax.swing.JTextField inputState;
    private javax.swing.JTextField inputStreet;
    private javax.swing.JFormattedTextField inputZipCode;
    private javax.swing.JLabel labelCity;
    private javax.swing.JLabel labelComplement;
    private javax.swing.JLabel labelCountry;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelLicenseNumber;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNumber;
    private javax.swing.JLabel labelPhoneNumber;
    private javax.swing.JLabel labelRole;
    private javax.swing.JLabel labelSocialSecurityNumber;
    private javax.swing.JLabel labelState;
    private javax.swing.JLabel labelStreet;
    private javax.swing.JLabel labelZipCode;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JRadioButton radioExisting;
    private javax.swing.ButtonGroup radioGroup;
    private javax.swing.JRadioButton radioNew;
    // End of variables declaration//GEN-END:variables
}
