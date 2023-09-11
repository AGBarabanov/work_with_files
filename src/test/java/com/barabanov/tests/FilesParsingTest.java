package com.barabanov.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

//    @Test
//    void xlsParseTest() throws Exception {
//        try(InputStream resourceAsStream = cl.getResourceAsStream("example.xlsx")){
//            XLS content = new XLS(resourceAsStream);
//            assertThat(content.excel.getSheetAt(0).getRow(1).getCell(1)).contains("example");
//        }
//    }

    @Test
    void csvParseTest() throws Exception {
        try(
                InputStream resource = cl.getResourceAsStream("example_csv.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(resource))
        ) {
          List<String[]> content = reader.readAll();
            assertThat(content.get(0)[1]).contains("lesson");
        }
    }

    @Test
    void zipParseTest() throws Exception {
        try(
                InputStream resource = cl.getResourceAsStream("Новый текстовый документ.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null){
                assertThat(entry.getName()).isEqualTo("Новый текстовый документ — копия — копия.txt");
            }
        }
    }


    @Test
    void jsonParseTest() throws Exception {
        Gson gson = new Gson();

        try(
                InputStream resource = cl.getResourceAsStream("glossary.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            assertThat(jsonObject.get("title").getAsString()).isEqualTo("example glossary");
            assertThat(jsonObject.get("GlossDiv").getAsJsonObject().get("title").getAsString()).isEqualTo("S");
            assertThat(jsonObject.get("GlossDiv").getAsJsonObject().get("flag").getAsBoolean()).isTrue();
        }
    }
}
