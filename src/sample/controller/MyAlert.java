package sample.controller;

import javafx.scene.control.Alert;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/8
 * Time: 2:31 下午
 */
class MyAlert {

    static void msg(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(info);
        alert.showAndWait();
    }

}
