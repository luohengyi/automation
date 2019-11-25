package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import sample.bean.AddressBookPerson;
import sample.bean.FirewallPerson;
import sample.bean.IpPerson;
import sample.service.AddressBookService;
import sample.service.FirewallService;
import sample.service.IpService;
import sample.util.UrlRule;


import java.util.List;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/22
 * Time: 2:38 下午
 */
public class BannedController {

    private FirewallService firewallService = FirewallService.build();
    private AddressBookService addressBookService = new AddressBookService();
    private IpService ipService = new IpService();

    @FXML
    TableView firewallTable;
    @FXML
    TableColumn firewallName;

    @FXML
    TableView addrTable;
    @FXML
    TableColumn addrName;

    @FXML
    TableView ipTable;
    @FXML
    TableColumn ip;

    @FXML
    public void initialize() {
        ObservableList<FirewallPerson> observableList = FXCollections.observableArrayList();
        List<FirewallPerson> dataList = firewallService.getDataList();
        observableList.addAll(dataList);

        firewallName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addrName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ip.setCellValueFactory(new PropertyValueFactory<>("ip"));

        firewallTable.setItems(observableList);

        firewallTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            FirewallPerson firewallPerson = (FirewallPerson) newValue;
            List<AddressBookPerson> firewallAddressBook = addressBookService.getFirewallAddressBook(firewallPerson.getId());
            ObservableList<AddressBookPerson> addressObservableList = FXCollections.observableArrayList();
            addressObservableList.addAll(firewallAddressBook);
            addrTable.setItems(addressObservableList);
        });
        IpPerson addIpPerson = new IpPerson();
        String ipd = "+添加";
        addIpPerson.setIp(ipd);

        addrTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                AddressBookPerson addressBookPerson = (AddressBookPerson) newValue;
                ObservableList<IpPerson> addressObservableList = FXCollections.observableArrayList();
                addressObservableList.addAll(ipService.getIpListByAddrId(addressBookPerson.getId()));
                addressObservableList.add(addIpPerson);
                ipTable.setItems(addressObservableList);
            }
        });

        ipTable.setRowFactory(tv -> {
            TableRow<IpPerson> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (row.getItem().getIp().equals(ipd)) {
                    //执行添加操作
                    System.out.println("添加按钮");
                }
            });
            return row;
        });


    }
}
