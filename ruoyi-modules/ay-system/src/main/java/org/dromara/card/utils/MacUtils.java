package org.dromara.card.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ay
 * 创建时间 2023-10-24
 * 通过ip地址获取mac
 */
public class MacUtils {
    public static String getLocalMac(String ipAddress){
        String str = "";
        String macAddress = "";
        // 获取非本地IP的MAC地址
        try {
//            System.out.println(ipAddress);
            Process p = Runtime.getRuntime()
                .exec("nbtstat -A " + ipAddress);
            System.out.println("===process==" + p);

            InputStreamReader ir = new InputStreamReader(p.getInputStream());

            BufferedReader br = new BufferedReader(ir);

            while ((str = br.readLine()) != null) {
                if (str.indexOf("MAC") > 1) {
                    macAddress = str.substring(str.indexOf("MAC") + 9, str.length());
                    macAddress = macAddress.trim();
//                    System.out.println("macAddress:" + macAddress);
                    break;
                }
            }
            p.destroy();
            br.close();
            ir.close();
        } catch (IOException ex) {
        }
        return macAddress;
    }
}
