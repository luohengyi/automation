package sample.controller;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.bean.AddressBook;
import sample.bean.AddressBookPerson;
import sample.bean.FirewallPerson;
import sample.service.AddressBookService;
import sample.util.UrlRule;

import java.util.List;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 8:40 上午
 * 地址薄
 */
public class AddressBookController {

    private static SimpleIntegerProperty firewallId = new SimpleIntegerProperty();
    private AddressBookService addressBookService = new AddressBookService();

    public static void setFirewallId(int firewallId) {
        AddressBookController.firewallId.set(firewallId);
    }

    @FXML
    TabPane table;

    @FXML
    private GridPane rootLout;

    @FXML
    private TableColumn id;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn operation;

    @FXML
    private Button addButton;
    @FXML
    private TextField addrName;
    @FXML
    private TableView addrTable;

    @FXML
    public void initialize() {
        autoChangeTableWith();
        initDataTable();
        addButton.setOnAction(event -> {
            String text = addrName.getText();
            AddressBook addressBook = new AddressBook(0, text, firewallId.getValue());
            boolean back = addressBookService.addAddressBook(addressBook);
            MyAlert.msg(back ? "添加成功" : addressBookService.getError());
            if (back){
                initDataTable();
                addrName.textProperty().setValue("");
            }
        });

    }

    private void initDataTable(){
        ObservableList observableList = FXCollections.observableArrayList();
        List<AddressBookPerson> dataList = addressBookService.getFirewallAddressBook(firewallId.getValue());
        observableList.addAll(dataList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addrTable.setItems(observableList);
        operation.setCellFactory((col) -> {
            TableCell<FirewallPerson, Integer> cell = new TableCell<FirewallPerson, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    //判断该行是否有行属否有数据
                    if (!empty) {
                        Button edit = new Button("编辑");
                        Button delete = new Button("删除");
                        delete.setId(getIndex() + "");
                        delete.setOnAction(event -> {
                            int id = Integer.parseInt(((Button) event.getSource()).getId());
                            boolean del = addressBookService.remove(dataList.get(id).getId());
                            initDataTable();
                            MyAlert.msg(del?"删除成功！":"删除失败！");
                        });
                        edit.setId(getIndex() + "");
                        edit.setOnAction(event -> {
                            int id = Integer.parseInt(((Button) event.getSource()).getId());
                            Addressbookedit.setAddrId(dataList.get(id).getId());
                            UrlRule.herf("addressBookEdit");
                        });
                        HBox pane = new HBox(edit, delete);
                        this.setGraphic(pane);
                    }
                }
            };
            return cell;
        });
    }

    private void autoChangeTableWith() {
        ReadOnlyDoubleProperty rootLoutWidthProperty = rootLout.widthProperty();
        //Observable
        ReadOnlyDoubleWrapper idbersProperty = new ReadOnlyDoubleWrapper();
        ReadOnlyDoubleWrapper nameProperty = new ReadOnlyDoubleWrapper();
        ReadOnlyDoubleWrapper operationProperty = new ReadOnlyDoubleWrapper();

        //监听 最外层GridPane的WidthProperty宽度的变化手动改变列的WidthProperty的值
        rootLoutWidthProperty.addListener(observable -> {
            //实际的长度
            double rootLoutWidth = rootLoutWidthProperty.get();
            idbersProperty.set(rootLoutWidth * 0.15);
            nameProperty.set(rootLoutWidth * 0.65);
            operationProperty.set(rootLoutWidth * 0.2);

        });
        id.prefWidthProperty().bind(idbersProperty);
        name.prefWidthProperty().bind(nameProperty);
        operation.prefWidthProperty().bind(nameProperty);

    }


}
