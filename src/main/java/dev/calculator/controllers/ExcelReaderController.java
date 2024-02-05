package dev.calculator.controllers;

import dev.calculator.models.EsquadriaModel;
import dev.calculator.services.imp.EsquadriaServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/leitor-excel")
public class ExcelReaderController {

    @Autowired
    EsquadriaServiceImpl excelReaderService;


    //@PostMapping("/read")
    @GetMapping("/read")
    public ResponseEntity<?> readAllExcel() {
        try {
            EsquadriaModel esquadria = excelReaderService.processarEImportarPlanilha("src/main/resources/in-csv/planilha-calculo.xlsx");
            return ResponseEntity.status(HttpStatus.OK).body(esquadria);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

/*
    @GetMapping("/processar-planilha")
    public ResponseEntity<List<String>> processarPlanilhaSemSalvar() {
        try {
            List<String> valores = excelReaderService.processarPlanilhaSemSalvar("src/main/resources/in-csv/planilha-calculo.xlsx");
            return ResponseEntity.ok(valores);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
*/

}