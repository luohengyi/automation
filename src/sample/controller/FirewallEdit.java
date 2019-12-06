package sample.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sample.bean.Firewall;
import sample.service.FirewallService;
import sample.service.HillStoneRestfulClient;
import sample.util.UrlRule;
import sample.util.alert.MyAlert;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 8:40 上午
 * 地址薄
 */
public class FirewallEdit {

    private static SimpleIntegerProperty id = new SimpleIntegerProperty();
    private FirewallService firewallService = FirewallService.build();
    @FXML
    private Button editFirewall;

    @FXML
    private GridPane rootLout;

    @FXML Button back;

    @FXML
    TextField firewallName;
    @FXML
    TextField ip;
    @FXML
    TextField port;
    @FXML
    TextField username;
    @FXML
    TextField password;

    @FXML
    private Button addFirewaTest;

    @FXML
    public void initialize(){
        //绑定返回值
        back.setOnAction(event -> {
            UrlRule.herf("firewall");
        });

        Firewall firewall = firewallService.getFirewall(id.getValue());
        firewallName.textProperty().set(firewall.getName());
        ip.textProperty().set(firewall.getIp());
        port.textProperty().set(firewall.getPort()+"");
        username.textProperty().set(firewall.getUsername());
        password.textProperty().setValue(firewall.getPassword());

        editFirewall.setOnAction(event -> {
            String portText = port.getText();
            Firewall firewall1 = new Firewall(id.getValue(),firewallName.getText(),ip.getText(), Integer.parseInt(portText.isEmpty()?"0":portText),username.getText(),password.getText());
            boolean edit = firewallService.edit(firewall1);
            MyAlert.msg(edit?"修改成功！":"修改失败！");
        });

        addFirewaTest.setOnAction(event -> {
            String portText = port.getText();
            Firewall firewall1 = new Firewall(id.getValue(),firewallName.getText(),ip.getText(), Integer.parseInt(portText.isEmpty()?"0":portText),username.getText(),password.getText());
            HillStoneRestfulClient hillStoneRestfulClient =  new HillStoneRestfulClient(firewall1);

//            ProgressFrom progressFrom = new ProgressFrom((Stage)rootLout.getScene().getWindow());
//            progressFrom.activateProgressBar();
            try {
                hillStoneRestfulClient.login();
//                progressFrom.cancelProgressBar();
                MyAlert.msg("测试成功");
            } catch (Exception e) {
                String message = e.getMessage();
//                progressFrom.cancelProgressBar();
                MyAlert.msg(message);
            }
        });

    }

    public static void setId(int id) {
        FirewallEdit.id.set(id);
    }
}
