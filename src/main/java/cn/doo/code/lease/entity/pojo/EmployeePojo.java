package cn.doo.code.lease.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("employee")
public class EmployeePojo {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String level;
}

