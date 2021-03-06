package sample.controller;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.bean.*;
import sample.service.AddressBookService;
import sample.service.FirewallService;
import sample.service.HillStoneRestfulClient;
import sample.service.IpService;
import sample.util.alert.MyAlert;

import java.util.List;

/**
 * @explain 域名下ip管理
 * @author 杜海江
 * @date 2020/1/19 16:22
 */
public class DomainIpController {

//    private FirewallService firewallService = FirewallService.build();
//    private AddressBookService addressBookService = new AddressBookService();
//    private IpService ipService = new IpService();
    private int thisAddrId = 0;

    @FXML
    private GridPane rootLout;
    @FXML
    TableView domainTable;
    @FXML
    TableColumn domainName;

    @FXML
    TableView ipTable;
    @FXML
    TableColumn ip;
    @FXML
    TableColumn ipDelete;

    @FXML
    public void initialize() {
        initWith();
//        ObservableList<FirewallPerson> observableList = FXCollections.observableArrayList();
//        List<FirewallPerson> dataList = firewallService.getDataList();
//        observableList.addAll(dataList);
//
//        domainName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        ip.setCellValueFactory(new PropertyValueFactory<>("ip"));
//
//        domainTable.setItems(observableList);
//
//        ipTable.setRowFactory(tv -> {
//            TableRow<IpPerson> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (row.getItem() != null) {
//                    if (row.getItem().getIp().equals("+添加")) {
//                        //执行添加操作
//                        MyAlert.TextAlert(res -> {
//                            if (null == res || res.trim().isEmpty()) {
//                                MyAlert.msg("ip地址不能为空");
//                                return;
//                            }
//                            IpPerson ipPerson = new IpPerson();
//                            ipPerson.setIp(res);
//                            ipPerson.setAddrId(thisAddrId);
//                            if (!ipService.isEnmty(ipPerson)){
//                                MyAlert.msg("ip地址已存在！");
//                                return;
//                            }
//
//                            AddressBook addressBook = addressBookService.getAddressBookbyId(thisAddrId);
//                            int firewallid = addressBook.getFirewallid();
//                            Firewall firewall = firewallService.getFirewall(firewallid);
//                            HillStoneRestfulClient hillStoneRestfulClient =  new HillStoneRestfulClient(firewall);
//                            try {
//                                hillStoneRestfulClient.updateAddrBook(addressBook.getName(),res,true);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                MyAlert.msg("添加失败！"+e.getMessage());
//                                return;
//                            }
//
//                            if (ipService.addIp(ipPerson)) {
//                                initIpTable(thisAddrId);
//                            }
//                        });
//                    }
//                }
//            });
//            return row;
//        });
    }

    public void initIpTable(int addressBookId) {
//        IpPerson addIpPerson = new IpPerson();
//        addIpPerson.setIp("+添加");
//        ObservableList<IpPerson> addressObservableList = FXCollections.observableArrayList();
//        List<IpPerson> dataList = ipService.getIpListByAddrId(addressBookId);
//        addressObservableList.addAll(dataList);
//        ipDelete.setCellValueFactory(new PropertyValueFactory<>("id"));
//        ipDelete.setCellFactory((col) -> {
//            TableCell<FirewallPerson, Integer> cell = new TableCell<FirewallPerson, Integer>() {
//                @Override
//                public void updateItem(Integer item, boolean empty) {
//                    //判断该行是否有行属否有数据
//                    if (!empty) {
//                        //判断数据长度
//                        if (!(this.getIndex() >= dataList.size())) {
//                            Button delete = new Button("删除");
//                            //实现删除逻辑
//                            delete.setId(getIndex() + "");
//                            delete.setOnAction(event -> {
//                                int id = Integer.parseInt(((Button) event.getSource()).getId());
//
//                                AddressBook addressBook = addressBookService.getAddressBookbyId(thisAddrId);
//                                int firewallid = addressBook.getFirewallid();
//                                Firewall firewall = firewallService.getFirewall(firewallid);
//                                HillStoneRestfulClient hillStoneRestfulClient =  new HillStoneRestfulClient(firewall);
//                                try {
//                                    hillStoneRestfulClient.updateAddrBook(addressBook.getName(),dataList.get(id).getIp(),false);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    MyAlert.msg( "删除失败！");
//                                }
//
//                                boolean del = ipService.deleteByAddrId(dataList.get(id).getIp());
//                                initIpTable(addressBookId);
//                                MyAlert.msg(del ? "删除成功！" : "删除失败！");
//                                //维护
//                            });
//                            HBox pane = new HBox(delete);
//                            this.setGraphic(pane);
//                        }
//                    }
//                }
//            };
//            return cell;
//        });
//        addressObservableList.add(addIpPerson);
//        ipTable.setItems(addressObservableList);
    }

    public void initWith(){
        ReadOnlyDoubleProperty rootLoutWidthProperty = domainTable.widthProperty();
        ReadOnlyDoubleProperty ipTableWidthProperty = ipTable.widthProperty();

        ReadOnlyDoubleWrapper firewallNameProperty = new ReadOnlyDoubleWrapper();
        ReadOnlyDoubleWrapper addrTableProperty = new ReadOnlyDoubleWrapper();
        ReadOnlyDoubleWrapper ipTableProperty = new ReadOnlyDoubleWrapper();
        ReadOnlyDoubleWrapper ipDeleteTableProperty = new ReadOnlyDoubleWrapper();
//        //监听 最外层GridPane的WidthProperty宽度的变化手动改变列的WidthProperty的值
        rootLoutWidthProperty.addListener(observable -> {
            //实际的长度
            double rootLoutWidth = rootLoutWidthProperty.get();
            firewallNameProperty.set(rootLoutWidth);
        });
        ipTableWidthProperty.addListener(observable -> {
            double ipTableTableWidth = ipTableWidthProperty.get();
            ipTableProperty.set(ipTableTableWidth*0.7);
            ipDeleteTableProperty.set(ipTableTableWidth*0.3);
        });

        domainName.prefWidthProperty().bind(firewallNameProperty);
        ip.prefWidthProperty().bind(ipTableProperty);
        ipDelete.prefWidthProperty().bind(ipDeleteTableProperty);
    }

}
