import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import com.excelinsight.util.ExcelUtil;
import com.excelinsight.entity.ExportSummaryRequest;

@RestController
@RequestMapping("/excel-insight/api")
public class FileController {

    @PostMapping("/export-summary")
    public void exportSummary(@RequestBody ExportSummaryRequest req, HttpServletResponse response) throws IOException {
        // 1. 读取Excel原始数据
        File file = new File("uploads/" + req.getFileName());
        if (!file.exists()) {
            response.setStatus(404);
            response.getWriter().write("文件不存在");
            return;
        }
        List<Map<String, Object>> rawData = ExcelUtil.readExcelAsMap(file);

        // 2. 过滤数据
        List<Map<String, Object>> filtered = ExcelUtil.filterData(rawData, req.getFilterConditions());

        // 3. 分组汇总
        List<Map<String, Object>> summary = ExcelUtil.groupAndAggregate(filtered, req.getDimensionFields(), req.getValueFields());

        // 4. 导出为Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"summary.xlsx\"");
        EasyExcel.write(response.getOutputStream())
                .head(ExcelUtil.buildHead(req.getDimensionFields(), req.getValueFields()))
                .sheet("汇总数据")
                .doWrite(summary);
    }
}
