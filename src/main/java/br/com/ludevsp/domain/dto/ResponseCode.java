package br.com.ludevsp.domain.dto;

import lombok.Getter;

@Getter
public class ResponseCode {
    public static final String  APROVADA = "00";
    public static final String  REJEITADA = "51";
    public static final String ERROINESPERADO = "07";
}
