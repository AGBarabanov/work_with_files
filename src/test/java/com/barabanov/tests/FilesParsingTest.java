package com.barabanov.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsingTest extends TestBase {

    ClassLoader cl = FilesParsingTest.class.getClassLoader();



    @Test
    void pdfParseTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");

        File pdfDownloadedfile = $("a[href='junit-user-guide-5.10.0.pdf']").download();
        PDF content = new PDF(pdfDownloadedfile);
        assertThat(content.author).contains("Sam Brannen");
    }

    @Test
    void xlsParseTest() throws Exception {
        try(InputStream resourceAsStream = cl.getResourceAsStream("example.xlsx")){
            XLS content = new XLS(resourceAsStream);
            assertThat(content.excel.getSheetAt(0).getRow(1).getCell(1)).contains("example");
        }
    }
}
