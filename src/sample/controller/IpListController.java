package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import sample.bean.IpPerson;
import sample.service.IpService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @explain ip信息筛选
 * @author 杜海江
 * @date 2020/1/16 10:24
 */
public class IpListController {

    @FXML
    private TextField search;

    @FXML
    private TableColumn ip_id;

    @FXML
    private TableColumn ip;

    @FXML
    private TableColumn ip_address;

    @FXML
    private void search(){
        List<IpPerson> search = new IpService().search();
        System.out.println(search);
    }


}
