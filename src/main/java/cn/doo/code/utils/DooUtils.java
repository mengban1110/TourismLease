package cn.doo.code.utils;

/*import cn.doo.code.order.entity.Order;*/

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author 梦伴
 * @desc
 * @time 2021-05-24-19:44
 */
public class DooUtils {

    /**
     * 封装的jsonMap
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Map<String, Object> print(int code, String msg, Object data, Object count) {
            HashMap<String, Object> map = new HashMap<String, Object>(4);
        map.put("code", code);
        map.put("msg", msg);
        if (data != null) {
            map.put("data", data);
        }
        if (count != null) {
            map.put("count", count);
        }
        return map;
    }

    /**
     * 计算page页数
     *
     * @param page
     * @param size
     * @return
     */
    public static Integer getCurrentPage(Integer page, Integer size) {
        if (page == 0) {
            page = 1;
        }
        page = (page - 1) * size;
        return page;
    }

    /**
     * MD5加密生成token
     *
     * @param field
     * @return
     */
    public static String getToken(String field) {
        String data = UUID.randomUUID().toString() + field;
        return makeMd5(data);
    }

    /**
     * MD5加密
     *
     * @param plainText
     * @return
     */
    public static String makeMd5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * 生成验证码
     *
     * @param count
     * @return
     */
    public static String makeCode(int count) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * List容器分页
     *
     * @param list
     * @param pageNum  页码
     * @param pageSize 每页多少条数据
     * @return
     */
//    public static List<Order> startPage(List<Order> list, Integer pageNum,
//                                        Integer pageSize) {
//        if (list == null) {
//            return null;
//        }
//        if (list.size() == 0) {
//            return null;
//        }
//
//        Integer count = list.size();
//        int pageCount = 0;
//        if (count % pageSize == 0) {
//            pageCount = count / pageSize;
//        } else {
//            pageCount = count / pageSize + 1;
//        }
//
//        int fromIndex = 0;
//        int toIndex = 0;
//
//        if (!pageNum.equals(pageCount)) {
//            fromIndex = (pageNum - 1) * pageSize;
//            toIndex = fromIndex + pageSize;
//        } else {
//            fromIndex = (pageNum - 1) * pageSize;
//            toIndex = count;
//        }
//
//        return list.subList(fromIndex, toIndex);
//    }
}
