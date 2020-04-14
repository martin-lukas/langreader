package org.lukas.langreader.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExpressionResponse {
    private int status;
    private String message;
    private long timeStamp;
}
