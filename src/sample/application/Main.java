package sample.application;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;
import sample.bean.FirewallPerson;
import sample.mapper.TestMapper;
import sample.util.SqlSessionFactoryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/sample.fxml"));
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add
                (Main.class.getResource("application.css").toExternalForm());
        primaryStage.setTitle("网络安全自动化工具");
        primaryStage.setScene(scene);
        primaryStage.show();
        //判断数据库是否加载正确！
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
        try {
            List<FirewallPerson> dataList = mapper.getDataList();
        }catch (Exception e){
            //处理数据表不存在
            String[] sqlFile = {"SELECT_t___FROM_FIREWALL_ADDRESSBOOK_t.sql","SELECT_t___FROM_FIREWALL_FIREWALL_t.sql",
                    "SELECT_t___FROM_FIREWALL_IPLIST_t.sql"};
//            System.out.println(this.getClass().getResource("/").getPath());


            for (String sqlf : sqlFile) {
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(sqlf);
                byte[] sql = new byte[1024];
                inputStream.read(sql);
                mapper.creatTable(new String(sql));
                inputStream.close();
            }
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
