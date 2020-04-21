package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@Getter
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private final String toCc;
}
