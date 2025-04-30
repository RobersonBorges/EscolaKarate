package br.com.karate.escola.EscolaKarate.geral.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {
    public static String obterDataString(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }
}
