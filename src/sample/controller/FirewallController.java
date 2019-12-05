package sample.controller;


import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.bean.Firewall;
import sample.bean.FirewallPerson;
import sample.service.FirewallService;
import sample.service.HillStoneRestfulClient;
import sample.util.ProgressFrom;
import sample.util.alert.MyAlert;
import sample.util.UrlRule;

import java.util.List;


/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/7
 * Time: 9:51 上午
 */
public class FirewallController {

    private FirewallService firewallService = FirewallService.build();

    @FXML
    private GridPane rootLout;

    @FXML
    private TableView tableFir;

    @FXML
    private TableColumn numbers;

    @FXML
    private TableColumn name;

    @FXML
    private TableColumn operation;

    @FXML
    private Button addFirewall;

    @FXML
    private TextField firewallName;
    @FXML
    private TextField ip;
    @FXML
    private TextField port;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private Tab listTab;
    @FXML
    private Button addFirewaTest;

    @FXML
    public void initialize() {

        this.autoChangeTableWith();
        initTableData();
        //添加防火墙
        addFirewall.setOnAction(event -> {
            StringProperty name = firewallName.textProperty();
            StringProperty ips = ip.textProperty();
            StringProperty ports = port.textProperty();
            StringProperty usernames = username.textProperty();
            StringProperty passwords = password.textProperty();
            Firewall firewall = new Firewall(0, name.getValue(), ips.getValue(), Integer.parseInt(ports.getValue().isEmpty() ? "0" : ports.getValue()), usernames.getValue(), passwords.getValue());
            boolean insert = firewallService.add(firewall);
            //维护输入框
            if (insert) {
                name.set("");
                ips.set("");
                ports.set("");
                usernames.set("");
                passwords.set("");
            }
            MyAlert.msg(insert ? "添加成功！" : firewallService.getError());
            //维护列表
            initTableData();
        });
        //防火墙连接测试
        addFirewaTest.setOnAction(event -> {
            String portText = port.getText();
            StringProperty name = firewallName.textProperty();
            StringProperty ips = ip.textProperty();
            StringProperty ports = port.textProperty();
            StringProperty usernames = username.textProperty();
            StringProperty passwords = password.textProperty();
            Firewall firewall = new Firewall(0, name.getValue(), ips.getValue(), Integer.parseInt(ports.getValue().isEmpty() ? "0" : ports.getValue()), usernames.getValue(), passwords.getValue());
            HillStoneRestfulClient hillStoneRestfulClient =  new HillStoneRestfulClient(firewall);

//            ProgressFrom progressFrom = new ProgressFrom((Stage)rootLout.getScene().getWindow());
//            progressFrom.activateProgressBar();

            try {
                hillStoneRestfulClient.login();
//                progressFrom.cancelProgressBar();
                MyAlert.msgRsAlert("测试成功");
            } catch (Exception e) {
                String message = e.getMessage();
//                progressFrom.cancelProgressBar();
                MyAlert.msgRsAlert(message);
            }
        });

    }

    /**
     * 初始化数据表格
     */
    private void initTableData() {
        ObservableList observableList = FXCollections.observableArrayList();
        List<FirewallPerson> dataList = firewallService.getDataList();
        observableList.addAll(dataList);
        numbers.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        operation.setCellFactory((col) -> {
            TableCell<FirewallPerson, Integer> cell = new TableCell<FirewallPerson, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    //判断该行是否有行属否有数据
                    if (!empty) {
                        Button edit = new Button("编辑");
                        Button delete = new Button("删除");
                        Button addr = new Button("地址薄");
                        //实现删除逻辑
                        delete.setId(getIndex() + "");
                        delete.setOnAction(event -> {
                            int id = Integer.parseInt(((Button) event.getSource()).getId());
                            boolean del = firewallService.delete(dataList.get(id).getId());

                            initTableData();
                            MyAlert.msg(del ? "删除成功！" : "删除失败！");
                            //维护
                        });
                        edit.setId(getIndex() + "");
                        edit.setOnAction(event -> {
                            int id = Integer.parseInt(((Button) event.getSource()).getId());
                            FirewallEdit.setId(dataList.get(id).getId());
                            UrlRule.herf("firewallEdit");
                        });
                        //地址薄管理
                        addr.setId(getIndex() + "");
                        addr.setOnAction(event -> {
                            int id = Integer.parseInt(((Button) event.getSource()).getId());
                            AddressBookController.setFirewallId(dataList.get(id).getId());
                            UrlRule.herf("AddressBook");
                        });
                        HBox pane = new HBox(edit, delete, addr);
                        this.setGraphic(pane);
                    }
                }
            };
            return cell;
        });
        tableFir.setItems(observableList);
    }


    //防火墙数据表格自动改变宽度
    private void autoChangeTableWith() {
        ReadOnlyDoubleProperty rootLoutWidthProperty = rootLout.widthProperty();
        //Observable
        ReadOnlyDoubleWrapper numbersProperty = new ReadOnlyDoubleWrapper();
        ReadOnlyDoubleWrapper nameProperty = new ReadOnlyDoubleWrapper();
        ReadOnlyDoubleWrapper operationProperty = new ReadOnlyDoubleWrapper();
        //监听 最外层GridPane的WidthProperty宽度的变化手动改变列的WidthProperty的值
        rootLoutWidthProperty.addListener(observable -> {
            //实际的长度
            double rootLoutWidth = rootLoutWidthProperty.get();
            numbersProperty.set(rootLoutWidth * 0.15);
            nameProperty.set(rootLoutWidth * 0.55);
            operationProperty.set(rootLoutWidth * 0.3);
        });

        numbers.prefWidthProperty().bind(numbersProperty);
        name.prefWidthProperty().bind(nameProperty);
        operation.prefWidthProperty().bind(operationProperty);
    }


}
