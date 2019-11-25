package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sample.util.UrlRule;

import java.io.IOException;


public class MainController {

    @FXML
    private MenuBar menu;

    @FXML
    private GridPane rootLayout;

    @FXML
    private BorderPane layout;


    @FXML
    public void initialize() {
        //初始化路由管理
        UrlRule.Build(layout);

        ObservableList<Menu> menus = menu.getMenus();
        for (Menu menu : menus) {
            ObservableList<MenuItem> items = menu.getItems();
            //便利菜单绑定时间
            for (MenuItem item : items) {
                item.setOnAction(event -> {
                    MenuItem source = (MenuItem) event.getSource();
                    String id = source.getId();
                    UrlRule.herf(id);
                });
            }
        }

    }

}
