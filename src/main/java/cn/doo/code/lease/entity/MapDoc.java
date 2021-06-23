package cn.doo.code.lease.entity;

import lombok.Data;

import java.util.List;

/**
 * 测试返回对象
 * @author 梦伴
 * @time 2021-06-06-21:02
 */
@Data
public class MapDoc<T> {
    String msg;
    int code;
    int count;
    List<T> data;
}
