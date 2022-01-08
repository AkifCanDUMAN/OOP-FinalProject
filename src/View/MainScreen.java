package View;

import DataBase.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainScreen implements Initializable
{
    @FXML private AnchorPane panelAnasayfa;
    @FXML private AnchorPane panelKitaplar;
    @FXML private AnchorPane panelOduncAl;
    @FXML private AnchorPane panelIadeEt;
    @FXML private AnchorPane panelKutuphaneBilgileri;
    @FXML private AnchorPane panelKitapEkle;
    @FXML private AnchorPane panelCikis;
    @FXML private AnchorPane panelProfil;
    @FXML private Label lblAnasayfa;
    @FXML private Label lblKitaplar;
    @FXML private Label lblKitapOduncAl;
    @FXML private Label lblKitapIadeEt;
    @FXML private Label lblKitapEkle;
    @FXML private Label lblKutuphaneBilgileri;
    @FXML private Label lblCikis;
    @FXML private TableView<Books> tableBook;
    @FXML private TableColumn<Books, String> columnBookKitapAdi;
    @FXML private TableColumn<Books, String> columnBookYazarAdi;
    @FXML private TableColumn<Books, String> columnBookSayfaSayisi;
    @FXML private TableColumn<Books, String> columnBookTuru;
    @FXML private TextField textBookName;
    @FXML private TextField textAuthorName;
    @FXML private TextField textNumberOfPage;
    @FXML private ComboBox<String> comboBoxType;
    @FXML private TextField textAdminUserName;
    @FXML private PasswordField textAdminPassword;
    @FXML private TextField textBookSearch;
    @FXML private TextField textBorrowUserName;
    @FXML private TextField textBorrowBookName;
    @FXML private TextField textReturnBookName;
    @FXML private TextField textReturnUserName;
    @FXML private Label textNumberOfBook;
    @FXML private Label labelNumberOfUser;
    @FXML private Label labelName;
    @FXML private Label labelSurname;
    @FXML private Label textProfilName;
    @FXML private Label textProfilUserName;
    @FXML private Label textProfilBorrowBook;
    @FXML private Button btnProfil;
    @FXML private Button btnBorrow;
    @FXML private Button btnReturn;
    @FXML private Button btnAdd;
    @FXML private Button btnExit;

    ObservableList<Books> listBooks;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        bookNumber();
        userNumber();

        labelName.setText(Login.nowNameAndSurname);
        labelSurname.setText(Login.nowUserName);

        addComboBoxType();
        comboBoxType.setValue("Type");

        textProfilUserName.setText(Login.nowUserName);
        textProfilName.setText(Login.nowNameAndSurname);

        profilBorrowBook();
        ViewTable();
    }

    private int menu = 1;

    @FXML
    void menuAnasayfaClick(MouseEvent event)
    {
        menuControl();
        panelAnasayfa.setVisible(true);
        menu = 1;
    }
    @FXML
    void menuCikisClick(MouseEvent event)
    {
        menuControl();
        panelCikis.setVisible(true);
        menu = 2;
    }
    @FXML
    void menuIadeEtClick(MouseEvent event)
    {
        menuControl();
        panelIadeEt.setVisible(true);
        menu = 3;
    }
    @FXML
    void menuKitapEkleClick(MouseEvent event)
    {
        menuControl();
        panelKitapEkle.setVisible(true);
        menu = 4;
    }
    @FXML
    void menuKitaplarClick(MouseEvent event)
    {
        menuControl();
        panelKitaplar.setVisible(true);
        menu = 5;
    }
    @FXML
    void menuKutuphaneBilgileriClick(MouseEvent event)
    {
        menuControl();
        panelKutuphaneBilgileri.setVisible(true);
        menu = 6;
    }
    @FXML
    void menuOduncAlClick(MouseEvent event)
    {
        menuControl();
        panelOduncAl.setVisible(true);
        menu = 7;
    }
    @FXML
    void menuProfilClick(MouseEvent event)
    {
        menuControl();
        panelProfil.setVisible(true);
        menu = 8;
    }
    public void menuControl()
    {
        switch (menu)
        {
            case 1:
                panelAnasayfa.setVisible(false);
                break;
            case 2:
                panelCikis.setVisible(false);
                break;
            case 3:
                panelIadeEt.setVisible(false);
                break;
            case 4:
                panelKitapEkle.setVisible(false);
                break;
            case 5:
                panelKitaplar.setVisible(false);
                break;
            case 6:
                panelKutuphaneBilgileri.setVisible(false);
                break;
            case 7:
                panelOduncAl.setVisible(false);
                break;
            case 8:
                panelProfil.setVisible(false);
                break;
            default:
                break;
        }
    }

    public void addComboBoxType()
    {
        ObservableList<String> list = FXCollections.observableArrayList("ROMAN","BİYOGRAFİ","HİKAYE","FELSEFE","KİŞİSEL GELİŞİM","POLİSİYE","TARİH","MASAL","ŞİİR","KURGU","MACERA");
        comboBoxType.setItems(list);
    }

    public void ViewTable()
    {
        columnBookKitapAdi.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        columnBookYazarAdi.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        columnBookSayfaSayisi.setCellValueFactory(new PropertyValueFactory<>("numberOfPage"));
        columnBookTuru.setCellValueFactory(new PropertyValueFactory<>("type"));

        try{
            BooksManager booksManager = new BooksManager();
            listBooks = booksManager.getDataBooks();
            tableBook.setItems(listBooks);
        }catch (SQLException exception)
        {
            System.out.println("Error");
            exception.printStackTrace();
        }

        FilteredList<Books> filteredList = new FilteredList<>(listBooks, e->true);
        textBookSearch.setOnKeyReleased(e ->{
            textBookSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
                filteredList.setPredicate((Predicate<? super Books>) Books ->{
                    if(newValue ==null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toUpperCase();
                    if(Books.getBookName().contains(lowerCaseFilter)){
                        return true;
                    }else if(Books.getAuthorName().contains(lowerCaseFilter)){
                        return true;
                    }else if(Books.getNumberOfPage().contains(lowerCaseFilter)){
                        return true;
                    }
                    else return Books.getType().contains(lowerCaseFilter);
                });
            } );
            SortedList<Books> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableBook.comparatorProperty());
            tableBook.setItems(sortedList);
        });
    }

    public void bookNumber()
    {
        BooksManager booksManager1 = new BooksManager();
        textNumberOfBook.setText(Integer.toString(booksManager1.numberOfBook()));
    }

    public void userNumber()
    {
        UsersManager usersManager = new UsersManager();
        labelNumberOfUser.setText(Integer.toString(usersManager.numberOfUser()));
    }

    public void profilBorrowBook()
    {
        BorrowBookManager borrowBookManager = new BorrowBookManager();
        textProfilBorrowBook.setText(borrowBookManager.userNameControl(Login.nowUserName));
    }

    @FXML
    public void clickExit(ActionEvent event)
    {
        System.exit(0);
    }

    @FXML
    public int addBookClick(ActionEvent event)
    {
        try{
            Integer.parseInt(textNumberOfPage.getText());
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Enter the correct number of pages.!");
            return 0;
        }
        if(comboBoxType.getValue().equals("Type") || textBookName.getText().equals("") || textAuthorName.getText().equals("") || textNumberOfPage.getText().equals("") || textAdminUserName.getText().equals("") || textAdminPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Missing Entry!!!");
        }
        else if(textAdminUserName.getText().equals("Admin") && textAdminPassword.getText().equals("123456"))
        {
            BooksManager booksManager = new BooksManager();
            booksManager.insertBook(textBookName.getText().toUpperCase(),textAuthorName.getText().toUpperCase(),textNumberOfPage.getText(),comboBoxType.getValue().toUpperCase());
            JOptionPane.showMessageDialog(null,"Entry added");
            textBookName.setText("");
            textAuthorName.setText("");
            textNumberOfPage.setText("");
            comboBoxType.setValue("Type");
            textAdminUserName.setText("");
            textAdminPassword.setText("");
            ViewTable();
            bookNumber();
            userNumber();
        }
        else {
            JOptionPane.showMessageDialog(null,"Incorrect or Incomplete Entry!!!");
        }
        return 0;
    }

    @FXML
    public void borrowBook(ActionEvent event)
    {
        BooksManager booksManager = new BooksManager();
        BorrowBookManager borrowBookManager = new BorrowBookManager();
        int control1 = booksManager.bookControl(textBorrowBookName.getText().toUpperCase());
        String control2 = borrowBookManager.userNameControl(Login.nowUserName);
        if(control1==0 && control2.equals("YOK") && Login.nowUserName.equals(textBorrowUserName.getText()))
        {
            booksManager.booksData(textBorrowBookName.getText().toUpperCase(),Login.nowUserName);
            JOptionPane.showMessageDialog(null,"Book Received");
            textBorrowBookName.setText("");
            textBorrowUserName.setText("");
            bookNumber();
            userNumber();
            ViewTable();
            profilBorrowBook();
        }
        else {
            if(control1 == 1) {
                JOptionPane.showMessageDialog(null,"There Is No Such Book.");
            }
            else if(!control2.equals("YOK")) {
                JOptionPane.showMessageDialog(null,"You already have a book borrowed right now.");
            }
            else if(!Login.nowUserName.equals(textBorrowUserName.getText())) {
                JOptionPane.showMessageDialog(null, "Username is incorrect");
            }
        }
    }

    @FXML
    public void returnBook(ActionEvent event)
    {
        BorrowBookManager borrowBookManager = new BorrowBookManager();
        int control = borrowBookManager.borrowBookControl(textReturnBookName.getText().toUpperCase());
        if(control==0 && Login.nowUserName.equals(textReturnUserName.getText()))
        {
            borrowBookManager.borrowBookData(textReturnBookName.getText().toUpperCase());
            JOptionPane.showMessageDialog(null,"Book Returned");
            textReturnBookName.setText("");
            textReturnUserName.setText("");
            textProfilBorrowBook.setText("None");
            bookNumber();
            userNumber();
            ViewTable();
        }
        else {
            if(control == 1){
                JOptionPane.showMessageDialog(null,"No Such Book Is Borrowed");
            }
            else if(!Login.nowUserName.equals(textReturnUserName.getText())) {
                JOptionPane.showMessageDialog(null,"Incorrect Username Entry");
            }
        }
    }


    @FXML
    public void enteredAnasayfa (MouseEvent event) { lblAnasayfa.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredKitaplar (MouseEvent event) { lblKitaplar.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredKitapOduncAl (MouseEvent event) { lblKitapOduncAl.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredKitapIadeEt (MouseEvent event) { lblKitapIadeEt.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredKitapEkle (MouseEvent event) { lblKitapEkle.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredKutuphaneBilgileri (MouseEvent event) { lblKutuphaneBilgileri.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredCikis (MouseEvent event) { lblCikis.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void exitedAnasayfa (MouseEvent event) { lblAnasayfa.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedKitaplar (MouseEvent event) { lblKitaplar.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedKitapOduncAl (MouseEvent event) { lblKitapOduncAl.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedKitapIadeEt (MouseEvent event) { lblKitapIadeEt.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedKitapEkle (MouseEvent event) { lblKitapEkle.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedKutuphaneBilgileri (MouseEvent event) { lblKutuphaneBilgileri.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedCikis (MouseEvent event) { lblCikis.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void btnEnteredProfil (MouseEvent event) { btnProfil.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedProfil (MouseEvent event) { btnProfil.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredBorrow (MouseEvent event) { btnBorrow.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedBorrow (MouseEvent event) { btnBorrow.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredReturn (MouseEvent event) { btnReturn.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedRetrun (MouseEvent event) { btnReturn.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredAdd (MouseEvent event) { btnAdd.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedAdd (MouseEvent event) { btnAdd.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredExit (MouseEvent event) { btnExit.styleProperty().set("-fx-background-color: #E57373"); }
    @FXML
    public void btnExitedExit (MouseEvent event) { btnExit.styleProperty().set("-fx-background-color:  #F44336"); }
}