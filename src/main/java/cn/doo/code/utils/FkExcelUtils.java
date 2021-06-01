package cn.doo.code.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author 梦伴
 */
public class FkExcelUtils {

    /**
     * 生成fk 并写出文件
     *
     * @param configuration cfg对象
     * @param templateName  模板名
     * @param data          map对象
     * @param outputStream  输出流
     * @throws IOException io异常
     * @throws TemplateException fk异常
     */
    private static void parse(Configuration configuration, String templateName, Map<String, Object> data, OutputStream outputStream) throws TemplateException, IOException {
        OutputStreamWriter writer = null;
        try {
            // 加载模板
            Template template = configuration.getTemplate(templateName, "UTF-8");
            // 填充数据至Excel
            writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            template.process(data, writer);
            writer.flush();
        } finally {
            // 关闭文件流
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * freemarker模版导出Excel(调用方法)
     * 扩展性强，适合复杂excel的导出
     *
     * @param configuration default-root.xml的freemarker视图解析器
     * @param response      HttpServletResponse
     * @param fileName      导出的文件名
     * @param templateName  模版名(路径默认请参考freemarkerConfig的配置)
     * @param data          写入的数据
     * @return 下载入口
     */
    public static String exportExcel(HttpServletResponse response, Configuration configuration, String templateName, Map<String, Object> data, String fileName) {
        String result = "系统提示：Excel文件导出成功！";
        // 以下开始输出到EXCEL
        try {
            // 定义输出流，以便打开保存对话框______________________begin

            // 取得输出流
            OutputStream outputStream = response.getOutputStream();

            // 清空输出流
            response.reset();

            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));

            // 定义输出类型
            response.setContentType("application/msexcel");

            // 定义输出流，以便打开保存对话框_______________________end

            //创建excel
            parse(configuration, templateName, data, outputStream);

            return result;
        } catch (Exception e) {
            result = "系统提示：Excel文件导出失败，原因：" + e.toString();
        }
        return result;
    }

}
