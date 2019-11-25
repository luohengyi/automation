package sample.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.bean.Firewall;
import sample.service.FirewallService;
import sample.util.UrlRule;

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
            firewallService.edit(firewall1);
        });

    }

    public static void setId(int id) {
        FirewallEdit.id.set(id);
    }
}
