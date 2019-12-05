package sample.util;


import sample.service.HillStoneRestfulClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HttpClent {
    String clientIp = "172.17.252.14";
    int clientPort = 443;
    String username = "zhaop6795";
    String password = "Sdfg@36186";

    public static void main(String[] args) throws IOException {
        try {
//            HillStoneRestfulClient zhaop67951 = new HillStoneRestfulClient("172.17.252.14", 80, "yangxd1192_api", "xxdd@1415&BJ", true);
//            List<Map<String, Object>> addrBook = zhaop67951.getAddrBook("test");
//            zhaop67951.updateAddrBook("test","127.0.0.1",false);
//            System.out.println(addrBook);
//            List<Map<String, Object>> result = zhaop67951.getAddrBook("test");
//            System.out.println(123);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
