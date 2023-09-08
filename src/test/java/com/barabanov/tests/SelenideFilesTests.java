package com.barabanov.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class SelenideFilesTests extends TestBase {

//    Если у кнопки нет href, используем это. Поднимается прокси-сервер
//    static {
//        Configuration.fileDownload = FileDownloadMode.PROXY;
//    }


    @Test
    void selenideDownloadTest() throws Exception {
        open("https://github.com/junit-team/junit5/blob/main/README.md");

        File downloadedFile = $("[data-testid = raw-button]").download();

        try(InputStream is = new FileInputStream(downloadedFile)){
            byte[] bytes = is.readAllBytes();
            String textContent = new String(bytes, StandardCharsets.UTF_8);
            assertThat(textContent).contains("Contributions to JUnit 5 are both welcomed and appreciated. For specific guidelines");
        }
    }


    @Test
    void selenideUploadFile(){
        open("https://fineuploader.com/demos.html");

        $("input[type='file']").uploadFromClasspath("zhabka.jpg");
        $(".qq-file-name").shouldHave(text("zhabka.jpg"));
    }
}
