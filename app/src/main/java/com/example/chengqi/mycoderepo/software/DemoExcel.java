package com.example.chengqi.mycoderepo.software;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chengqi.mycoderepo.R;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DemoExcel extends AppCompatActivity {

    private static final String TAG = "DemoExcel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_excel);

        doExcel();
    }

    private void doExcel() {
//        New Workbook
        try {
            String path = getFilesDir() + "/" + "workbook.xls";

            InputStream inp = new FileInputStream(path);

            HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

            for (Sheet sheet : wb) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String s = null;
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            s = cell.getStringCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            s = String.valueOf(cell.getNumericCellValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                            String form = cell.getCellFormula();
                            Log.d(TAG, "form=" + form);
                            CellValue cellValue = evaluator.evaluate(cell);
                            switch (cellValue.getCellType()) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                    s = "" + cellValue.getBooleanValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    s = "" + cellValue.getNumberValue();
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    s = "" + cellValue.getStringValue();
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    break;
                                case Cell.CELL_TYPE_ERROR:
                                    break;

                                // CELL_TYPE_FORMULA will never happen
                                case Cell.CELL_TYPE_FORMULA:
                                    break;
                            }
                        }
                        Log.d(TAG, "[" + row.getRowNum() + "][" + cell.getColumnIndex() + "]" + s);
                    }
                }
            }

            Sheet sheet1 = wb.getSheetAt(0);
            Row row1 = sheet1.getRow(1);
            Cell cell1 = row1.getCell(1);
            Log.d(TAG, "pre=" + cell1.getNumericCellValue());
            cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell1.setCellValue(cell1.getNumericCellValue() + 1);
            Log.d(TAG, "aft=" + cell1.getNumericCellValue());

            wb.getCreationHelper().createFormulaEvaluator().evaluateAll();

            FileOutputStream fileOut = new FileOutputStream(path);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
            inp.close();

            inp = new FileInputStream(path);
            wb = new HSSFWorkbook(new POIFSFileSystem(inp));
            evaluator = wb.getCreationHelper().createFormulaEvaluator();

            for (Sheet sheet : wb) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String s = null;
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            s = cell.getStringCellValue();
                        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            s = String.valueOf(cell.getNumericCellValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                            CellValue cellValue = evaluator.evaluate(cell);
                            switch (cellValue.getCellType()) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                    s = "" + cellValue.getBooleanValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    s = "" + cellValue.getNumberValue();
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    s = "" + cellValue.getStringValue();
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    break;
                                case Cell.CELL_TYPE_ERROR:
                                    break;

                                // CELL_TYPE_FORMULA will never happen
                                case Cell.CELL_TYPE_FORMULA:
                                    break;
                            }
                        }
                        Log.d(TAG, "[" + row.getRowNum() + "][" + cell.getColumnIndex() + "]" + s);
                    }
                }
            }

            wb.close();
            inp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

