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
import sample.util.alert.MyAlert;


import java.util.Arrays;
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
    private int thisAddrId = 0;

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
    TableColumn ipDelete;

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


        addrTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                AddressBookPerson addressBookPerson = (AddressBookPerson) newValue;
                thisAddrId = addressBookPerson.getId();
                initIpTable(thisAddrId);
            }
        });

        ipTable.setRowFactory(tv -> {
            TableRow<IpPerson> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(row.getItem() != null){
                    if (row.getItem().getIp().equals("+添加")) {
                        //执行添加操作
                        MyAlert.TextAlert(res -> {
                            if (null==res||res.trim().isEmpty()){
                                MyAlert.msg("ip地址不能为空");
                                return;
                            }
                            IpPerson ipPerson = new IpPerson();
                            ipPerson.setIp(res);
                            ipPerson.setAddrId(thisAddrId);
                            if (ipService.addIp(ipPerson)){
                                initIpTable(thisAddrId);
                            }
                        });
                    }
                }
            });
            return row;
        });
    }

    public void initIpTable(int addressBookId){
        IpPerson addIpPerson = new IpPerson();
        addIpPerson.setIp("+添加");
        ObservableList<IpPerson> addressObservableList = FXCollections.observableArrayList();
        List<IpPerson> dataList = ipService.getIpListByAddrId(addressBookId);
        addressObservableList.addAll(dataList);
        ipDelete.setCellValueFactory(new PropertyValueFactory<>("id"));
        ipDelete.setCellFactory((col) -> {
            TableCell<FirewallPerson, Integer> cell = new TableCell<FirewallPerson, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    //判断该行是否有行属否有数据
                    if (!empty) {
                        //判断数据长度
                        if(!(this.getIndex() >= dataList.size())){
                            Button delete = new Button("删除");
                            //实现删除逻辑
                            delete.setId(getIndex() + "");
                            delete.setOnAction(event -> {
                                int id = Integer.parseInt(((Button) event.getSource()).getId());
                                boolean del = ipService.deleteByAddrId(dataList.get(id).getIp());
                                initIpTable(addressBookId);

                                MyAlert.msg(del ? "删除成功！" : "删除失败！");
                                //维护
                            });
                            HBox pane = new HBox(delete);
                            this.setGraphic(pane);
                        }
                    }
                }
            };
            return cell;
        });
        addressObservableList.add(addIpPerson);
        ipTable.setItems(addressObservableList);
    }

}
