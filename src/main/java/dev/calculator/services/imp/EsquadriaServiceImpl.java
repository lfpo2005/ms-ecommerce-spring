package dev.calculator.services.imp;


import dev.calculator.models.EsquadriaModel;
import dev.calculator.repositories.EsquadriaRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EsquadriaServiceImpl {

    @Autowired
    private EsquadriaRepository esquadriaRepository;

    //@Transactional
    public EsquadriaModel processarEImportarPlanilha(String caminhoArquivo) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(caminhoArquivo));
        Sheet sheet = workbook.getSheetAt(0);

        EsquadriaModel esquadria = new EsquadriaModel();

        esquadria.setTitulo(sheet.getRow(0).getCell(0).getStringCellValue());

        List<String> perfis = new ArrayList<>();
        for (int i = 10; i <= 19; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    perfis.add(cell.getStringCellValue());
                }
            }
        }
        List<String> descricoes = new ArrayList<>();
        for (int i = 10; i <= 19; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(1);
                if (cell != null) {
                    descricoes.add(cell.getStringCellValue());
                }
            }
        }
        List<Integer> quantidades = new ArrayList<>();
        for (int i = 10; i <= 19; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(5);
                if (cell != null) {
                    if (isMergedRegion(sheet, cell)) {
                        double numericValue = cell.getNumericCellValue();
                        quantidades.add((int) numericValue);
                    }
                }
            }
        }
        List<String> referencias = new ArrayList<>();
        for (int i = 10; i <= 19; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(7);
                if (cell != null) {
                    if (isMergedRegion(sheet, cell)) {
                        if (cell.getCellType() == CellType.STRING) {
                            referencias.add(cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            referencias.add(String.valueOf((int) cell.getNumericCellValue()));
                        }
                    }
                }
            }
        }
        List<String> graus = new ArrayList<>();
        for (int i = 10; i <= 19; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(9);
                if (cell != null) {
                    if (isMergedRegion(sheet, cell)) {
                        if (cell.getCellType() == CellType.STRING) {
                            graus.add(cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            graus.add(String.valueOf((int) cell.getNumericCellValue()));
                        }
                    }
                }
            }
        }
        esquadria.setPerfil(perfis);
        esquadria.setDescricao(descricoes);
        esquadria.setQuantidade(quantidades);
        esquadria.setReferencia(referencias);
        esquadria.setGrau(graus);
        workbook.close();
        return esquadria;
    }
    private boolean isMergedRegion(Sheet sheet, Cell cell) {
        int cellRow = cell.getRowIndex();
        int cellColumn = cell.getColumnIndex();

        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.isInRange(cellRow, cellColumn)) {
                return true;
            }
        }
        return false;
    }
}
/*    public List<String> processarPlanilhaSemSalvar(String caminhoArquivo) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(caminhoArquivo));
        Sheet sheet = workbook.getSheetAt(0);

        List<String> valores = new ArrayList<>();

        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell != null) {
                    String cellReference = new CellReference(cell).formatAsString();
                    String cellValue = "";
                    if (cell.getCellType() == CellType.STRING) {
                        cellValue = cell.getStringCellValue();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    if (cellValue != null && !cellValue.isEmpty()) {
                        valores.add(cellReference + " " + cellValue);
                    }
                }
            }
        }

        workbook.close();
        return valores;
    }
*/

//    @Transactional
//    public EsquadriaModel processarEImportarPlanilha(String caminhoArquivo) throws IOException {
//        Workbook workbook = WorkbookFactory.create(new File(caminhoArquivo));
//        Sheet sheet = workbook.getSheetAt(0);
//
//        // Pode ser necessário um loop aqui se você estiver processando várias esquadrias
//        EsquadriaModel esquadria = new EsquadriaModel();
//        // Defina as propriedades da esquadria aqui...
//
//        // Processamento das linhas de acessórios
//        int indiceInicioAcessorios = 34; // Por exemplo, se os acessórios começarem na linha 35
//        for (int i = indiceInicioAcessorios; i <= sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//            if (row == null || row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
//                continue; // Ignora linhas vazias ou sem código de acessório
//            }
//
//            AcessorioModel acessorio = new AcessorioModel();
//            // Supondo que a coluna 0 tenha o código do acessório
//            acessorio.setCodigo(row.getCell(0).getStringCellValue());
//            // ... Defina outras propriedades do acessório a partir das células correspondentes...
//
//            acessorio.setEsquadria(esquadria); // Associa o acessório à esquadria
//            esquadria.getAcessorios().add(acessorio); // Adiciona o acessório à lista na esquadria
//        }
//
//        // Salva a esquadria no banco de dados, que por sua vez salva os acessórios associados
//        esquadriaRepository.save(esquadria);
//
//        workbook.close();
//
//        return esquadria;
//    }


