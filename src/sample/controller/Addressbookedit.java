package sample.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.bean.AddressBook;
import sample.service.AddressBookService;
import sample.util.UrlRule;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 8:40 上午
 * 地址薄
 */
public class Addressbookedit {

    private static SimpleIntegerProperty addrId = new SimpleIntegerProperty();
    private AddressBookService addressBookService = new AddressBookService();

    public static void setAddrId(int addrId) {
        Addressbookedit.addrId.set(addrId);
    }

    @FXML
    private TextField addrName;

    @FXML
    private Button back;

    @FXML
    private Button determine;

    @FXML
    public void initialize() {
        AddressBook addressBook = addressBookService.getAddressBookbyId(Addressbookedit.addrId.getValue());
        addrName.textProperty().setValue(addressBook.getName());

        back.setOnAction(event -> {
            UrlRule.herf("AddressBook");
        });

        determine.setOnAction(event -> {
            String nameText = addrName.getText();
            addressBook.setName(nameText);
            boolean back = addressBookService.edit(addressBook);
            MyAlert.msg(back ? "修改成功！" : addressBookService.getError());
        });


    }


}
