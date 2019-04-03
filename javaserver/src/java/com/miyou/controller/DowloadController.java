package com.miyou.controller;

import com.miyou.model.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/dowload")
public class DowloadController {



    @RequestMapping("/testFile")
    @ResponseBody
    public ResponseEntity<byte[]> index(ServletRequest request) {

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("1");// 分页
        Sheet sheet2 = wb.createSheet("2");// 分页2

        Row row = sheet.createRow(0);// 第0+1行
        Cell cell = row.createCell(0);// 第row行第0+1列
        cell.setCellValue("abc");
        // 把所有的数据放在excel中

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "1workbook.xls");// new String("线上消费记录".getBytes("GBK"),"iso-8859-1")
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        try {
            wb.write(outByteStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(outByteStream.toByteArray(),headers, HttpStatus.CREATED);
    }
}
