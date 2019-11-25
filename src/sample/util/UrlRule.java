package sample.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 8:52 上午
 * 获取配置文件中的路由
 */
public class UrlRule {

    private static BorderPane layout;

    public static void Build(BorderPane layout) {
        UrlRule.layout = layout;
    }

    /**
     * 页面跳转
     * @param urlName 路由名称
     */
    public static void herf(String urlName){
        Parent root = null;
        try {
            //通过id 获取 url 配置中对应的 地址
            root = FXMLLoader.load(UrlRule.class.getResource(UrlRule.getUrl(urlName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout.setCenter(root);
    }

    public static String getUrl(String urlName) throws IOException {
        InputStream resourceAsStream = UrlRule.class.getClassLoader().getResourceAsStream("url.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String url =  properties.get(urlName).toString();
        if (resourceAsStream != null)
            resourceAsStream.close();
        return url;
    }

}
