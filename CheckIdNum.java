package ali;

/**
 * Created by 符光辉 on 2019/4/28.
 * 获得两个String类型参数（姓名和身份证号码）
 * 调用阿里云API验证身份证号码
 * 返回true/false
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class CheckIdNum {

    public static String  checkIdNum(String idNum,String realName) {
        String s1 = "true";
        String s2 = "false";
        String returnStr = null; // 返回结果定义
        URL url = null;

        String param = "idCard="+idNum+"&name="+realName;//参数
        String strUrl = "http://naidcard.market.alicloudapi.com/nidCard";//接口地址（测试完达到使用次数就换）
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(strUrl + "?" + param);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Authorization", "APPCODE " + "ff76f95c53bd45169d49b8f4d221c83f");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET"); // get方式
            httpURLConnection.setUseCaches(false); // 不用缓存
            httpURLConnection.connect();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            returnStr = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        if (returnStr.contains("实名认证通过！"))
            return s1;

        else
            return s2;
    }

}