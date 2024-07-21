package br.com.ludevsp.domain.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransationKey implements Serializable {
    private long transationCode;
}
