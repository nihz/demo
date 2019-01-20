package com.ssf.poi.demo;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class PoiDemo {

    public static void main(String[] args) throws IOException {

        //Blank Document
        /*XWPFDocument document = new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(
                new File("createdocument.docx"));
        document.write(out);
        out.close();
        System.out.println(
                "createdocument.docx written successully");


        XWPFDocument docx = new XWPFDocument(
                new FileInputStream("/Users/heikki/Downloads/审计记录模版.docx"));
        //using XWPFWordExtractor Class
        XWPFWordExtractor we = new XWPFWordExtractor(docx);
        System.out.println(we.getText());*/

        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(
                new File("审计记录-测试.docx"));


        XWPFTable table = document.createTable();
        XWPFParagraph paragraph = document.createParagraph();
        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);


        table.setWidth(100);

        tableRowOne.getCell(0).setText("被审计单位名称");
        tableRowOne.addNewTableCell().setText("ssf");
        tableRowOne.addNewTableCell();
        tableRowOne.addNewTableCell();
        //create second row
        XWPFTableRow tableRowTwo = table.createRow();
        tableRowTwo.getCell(0).setText("审计事项");
        tableRowTwo.getCell(1).setText("哈哈");
        //create third row
        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText("会计期间或截至日期");
        tableRowThree.getCell(1).setText(new Date().toString());

        XWPFTableRow tableRowFour = table.createRow();
        tableRowFour.getCell(0).setText("审计人员");
        tableRowFour.getCell(1).setText("SSF");
        tableRowFour.getCell(2).setText("编制日期");
        tableRowFour.getCell(3).setText(new Date().toString());

        mergeCellsHorizontal(table, 1, 2, 3);
        mergeCellsHorizontal(table, 2, 2, 3);
        mergeCellsHorizontal(table, 3, 2, 3);

        document.write(out);
        out.close();


    }


    private static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }


    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}
