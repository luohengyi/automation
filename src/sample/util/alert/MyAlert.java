package sample.util.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/8
 * Time: 2:31 下午
 */
public class MyAlert {

    public static void msg(String info) {
        msgRsAlert(info);
    }

    public static Alert msgRsAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(info);
        alert.showAndWait();
        return alert;
    }

    public static void TextAlert(AlertTestRes alertTestRes) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("添加ip");
        dialog.setHeaderText("");
        // 传统的获取输入值的方法
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(alertTestRes::calculate);
    }
}
