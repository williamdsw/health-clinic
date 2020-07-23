package view;

import controller.service.AddressService;
import controller.service.PatientService;
import controller.service.ZipCodeService;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import model.Address;
import model.Patient;
import model.ZipCode;

public class CreateUpdatePatient extends javax.swing.JFrame {
    
    // FIELDS
    
    private Patient patient = null;
    private final AddressService addressService = new AddressService();
    private final PatientService service = new PatientService();
    private final ZipCodeService zipCodeService = new ZipCodeService();

    // FUNCTIONS
    
    private void setComboLists () {
        
        try {

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
            System.out.println("CreateUpdatePatient:51 = " + ex.getMessage());
        }
    }
    
    private void toggleAddressComponents (boolean flag) {
        addressPanel.setEnabled(flag);
        
        for (Component component : addressPanel.getComponents()) {
            component.setEnabled(flag);
        }
    }
    
    private void setComponentsDefaultValues () {
        
        try {
            this.setTitle("Patient");
            this.setResizable(false);
            comboAddress.setEnabled(false);
            checkAddress.setSelected(false);
            radioExisting.setSelected(false);
            radioExisting.setEnabled(false);
            radioNew.setSelected(false);
            radioNew.setEnabled(false);
            toggleAddressComponents(false);

            Font labelFont = new Font("Monospaced", Font.BOLD, 16);
            Font inputFont = new Font("Monospaced", Font.PLAIN, 16);

            JLabel[] labels = {
                labelCity, labelComplement, labelName, labelNumber, 
                labelSocialSecurityNumber, labelState, labelStreet, labelZipCode, 
                labelCountry, labelEmail, labelPhoneNumber
            };

            JComponent[] inputs = {
                inputCity, inputComplement, inputName, inputNumber,
                inputSocialSecurityNumber, inputState, inputStreet, inputZipCode,
                inputCountry, inputEmail, inputPhoneNumber, checkAddress, radioExisting,
                radioNew, comboAddress
            };

            String[] labelsTexts = {
                "* City ", "Complement", "* Name ", "Number", "* SSN ", 
                "* State ", "* Street ", "* Zip Code ", "* Country", "* Email", 
                "Phone Number"
            };

            int index = 0;
            for (JLabel label : labels) {
                label.setText(labelsTexts[index]);
                label.setFont(labelFont);
                index++;
            }

            for (JComponent component : inputs) {
                component.setFont(inputFont);
            }

            buttonSave.setFont(labelFont);
            addressPanel.setFont(labelFont);

            JFormattedTextField.AbstractFormatter ssnFormatter = new MaskFormatter("###-##-####");
            JFormattedTextField.AbstractFormatter zipCodeFormatter = new MaskFormatter("#####");
            inputSocialSecurityNumber.setFormatterFactory(new DefaultFormatterFactory(ssnFormatter));
            inputZipCode.setFormatterFactory(new DefaultFormatterFactory(zipCodeFormatter));
        } 
        catch (Exception ex) {
            System.out.println("");
        }
    }
    
    private void validation (boolean isUpdate) {
        Set<String> requiredFields = new HashSet<>();

        if (inputName.getText().isEmpty()) {
            requiredFields.add(labelName.getText());
        }

        if (inputSocialSecurityNumber.getText().isEmpty() || inputSocialSecurityNumber.getText().trim().length() != 11) {
            requiredFields.add(labelSocialSecurityNumber.getText());
        }

        if (inputEmail.getText().isEmpty()) {
            requiredFields.add(labelEmail.getText());
        }

        if (checkAddress.isSelected() && radioExisting.isSelected()) {
            if (comboAddress.getSelectedIndex() == 0) {
                requiredFields.add("* Existing Address");
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
            save(isUpdate);
        } 
        else {
            JOptionPane.showMessageDialog(this, requiredFields);
        }
    }
    
    private void save (boolean isUpdate) {
        
        try {
            Timestamp createdAt = (isUpdate ? patient.getCreatedAt() : new Timestamp(System.currentTimeMillis ()));
            String email = inputEmail.getText();
            Integer id = (isUpdate ? patient.getId() : 0);
            String name = inputName.getText();
            String phoneNumber = inputPhoneNumber.getText();
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
                    address.setId(isUpdate ? patient.getAddress().getId() : 0);
                    address.setCity(inputCity.getText());
                    address.setComplement(inputComplement.getText());
                    address.setCountry(inputCountry.getText());
                    address.setCreatedAt(isUpdate ? patient.getAddress().getCreatedAt(): new Timestamp(System.currentTimeMillis()));
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
            
                patient = (patient != null ? patient : new Patient());
                patient.setId(id);
                patient.setCreatedAt(createdAt);
                patient.setEmail(email);
                patient.setName(name);
                patient.setPhoneNumber(phoneNumber);
                patient.setSocialSecurityNumber(socialSecurityNumber);
                patient.setUpdatedAt(updatedAt);
                patient.setAddress(address);

                boolean success = (isUpdate ? service.update(patient) : service.insert(patient));
                if (success) {
                    JOptionPane.showConfirmDialog(this, "Operation has succeed! Go to patient search?", "Success", JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
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
        
        inputZipCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ev) {
                
                try {
                    String zipCode = inputZipCode.getText();
                    String regex = "[0-9]{5}";
                    if (zipCode.trim().length() == 5) {
                        if (zipCode.matches(regex) && ev.getKeyCode() == ev.VK_ENTER) {
                            
                            inputZipCode.setEnabled(false);

                            ZipCode zipCodeResult = zipCodeService.search(zipCode);
                            if (zipCodeResult.getError() != null) {
                                inputZipCode.setText(null);
                                inputZipCode.setEnabled(true);
                                JOptionPane.showMessageDialog(CreateUpdatePatient.this, zipCodeResult.getError());
                            } 
                            else {
                                inputZipCode.setEnabled(true);
                                inputCity.setText(zipCodeResult.getCity());
                                inputState.setText(zipCodeResult.getState());
                                inputCountry.setText(zipCodeResult.getCountry());
                            }
                        } 
                    }
                } 
                catch (Exception e) {
                    System.out.println("e: " + e.getMessage());
                }
            }
        });
        
        buttonSave.addActionListener ((ev) -> {
            
            System.out.println ("Clicked");
            validation(false);
            
        });
    }

    public CreateUpdatePatient() {
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
    private void initComponents()
    {

        radioGroup = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        labelSocialSecurityNumber = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        labelPhoneNumber = new javax.swing.JLabel();
        inputName = new javax.swing.JTextField();
        inputEmail = new javax.swing.JTextField();
        inputPhoneNumber = new javax.swing.JTextField();
        inputSocialSecurityNumber = new javax.swing.JFormattedTextField();
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
        separator = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee");
        setLocation(new java.awt.Point(490, 200));
        setName("frm_CADASTRAR"); // NOI18N

        mainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelName.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelName.setText("* Name:");
        labelName.setName("lbl_NOME"); // NOI18N

        labelSocialSecurityNumber.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelSocialSecurityNumber.setText("* SSN:");
        labelSocialSecurityNumber.setName("lbl_CPF"); // NOI18N

        labelEmail.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelEmail.setText("* E-mail:");
        labelEmail.setName("lbl_CPF"); // NOI18N

        labelPhoneNumber.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelPhoneNumber.setText("Phone Number:");
        labelPhoneNumber.setName("lbl_CPF"); // NOI18N

        inputName.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        inputName.setName("txt_NOME"); // NOI18N

        inputEmail.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        inputEmail.setName("txt_NOME"); // NOI18N

        inputPhoneNumber.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        inputPhoneNumber.setName("txt_NOME"); // NOI18N

        inputSocialSecurityNumber.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        buttonSave.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.setName("bnt_CADASTRAR"); // NOI18N

        addressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Address", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 16))); // NOI18N

        labelCity.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelCity.setText("City:");
        labelCity.setName("lbl_CIDADE"); // NOI18N

        labelStreet.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelStreet.setText("Street");
        labelStreet.setName("lbl_BAIRRO"); // NOI18N

        labelZipCode.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelZipCode.setText("Zip Code:");
        labelZipCode.setName("lbl_CEP"); // NOI18N

        labelNumber.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelNumber.setText("Number:");
        labelNumber.setName("lnl_NUM"); // NOI18N

        labelState.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelState.setText("State:");
        labelState.setName("lbl_UF"); // NOI18N

        labelComplement.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelComplement.setText("Complement:");
        labelComplement.setName("lbl_COMPLEMENTO"); // NOI18N

        labelCountry.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        labelCountry.setText("Country:");
        labelCountry.setName("lbl_UF"); // NOI18N

        inputCity.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        inputCity.setName("txt_CIDADE"); // NOI18N

        inputStreet.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        inputStreet.setName("txt_BAIRRO"); // NOI18N

        inputNumber.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        inputNumber.setName("txt_NUM"); // NOI18N

        inputComplement.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        inputComplement.setName("txt_COMPLEMENTO"); // NOI18N

        inputState.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        inputCountry.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        inputZipCode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        inputZipCode.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        javax.swing.GroupLayout addressPanelLayout = new javax.swing.GroupLayout(addressPanel);
        addressPanel.setLayout(addressPanelLayout);
        addressPanelLayout.setHorizontalGroup(
            addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(labelStreet)
                                .addGap(152, 152, 152))
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(inputStreet)
                                .addGap(18, 18, 18)))
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNumber)
                            .addComponent(labelState))
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(labelCountry)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputComplement)
                                    .addGroup(addressPanelLayout.createSequentialGroup()
                                        .addComponent(labelComplement)
                                        .addGap(0, 275, Short.MAX_VALUE)))
                                .addContainerGap())))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelCity)
                                    .addComponent(inputCity, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(inputState)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(labelZipCode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
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
                        .addComponent(inputStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelComplement)
                            .addComponent(labelNumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComplement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(inputNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addComponent(labelState)
                        .addGap(29, 29, 29))
                    .addGroup(addressPanelLayout.createSequentialGroup()
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(addressPanelLayout.createSequentialGroup()
                                .addComponent(labelCity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addressPanelLayout.createSequentialGroup()
                                .addComponent(labelCountry)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        labelCity.getAccessibleContext().setAccessibleName("");

        checkAddress.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        checkAddress.setText("Address");

        radioGroup.add(radioExisting);
        radioExisting.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        radioExisting.setText("Existing");

        radioGroup.add(radioNew);
        radioNew.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        radioNew.setText("New");

        comboAddress.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(labelName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboAddress, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(separator)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(checkAddress)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioExisting)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioNew)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(addressPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(inputEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                    .addComponent(labelEmail)
                                    .addComponent(inputName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputPhoneNumber)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelSocialSecurityNumber)
                                            .addComponent(labelPhoneNumber))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(inputSocialSecurityNumber))))))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(labelName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(labelEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(labelSocialSecurityNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputSocialSecurityNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPhoneNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkAddress)
                    .addComponent(radioExisting)
                    .addComponent(radioNew))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        labelSocialSecurityNumber.getAccessibleContext().setAccessibleName("");

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
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(CreateUpdatePatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateUpdatePatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateUpdatePatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateUpdatePatient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateUpdatePatient().setVisible(true);
            }
        });
    }
    
    // COMPONENTS

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addressPanel;
    private javax.swing.JButton buttonSave;
    private javax.swing.JCheckBox checkAddress;
    private javax.swing.JComboBox<String> comboAddress;
    private javax.swing.JTextField inputCity;
    private javax.swing.JTextField inputComplement;
    private javax.swing.JTextField inputCountry;
    private javax.swing.JTextField inputEmail;
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
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelNumber;
    private javax.swing.JLabel labelPhoneNumber;
    private javax.swing.JLabel labelSocialSecurityNumber;
    private javax.swing.JLabel labelState;
    private javax.swing.JLabel labelStreet;
    private javax.swing.JLabel labelZipCode;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JRadioButton radioExisting;
    private javax.swing.ButtonGroup radioGroup;
    private javax.swing.JRadioButton radioNew;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables
}
