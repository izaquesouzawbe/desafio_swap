package com.desafioswap.webhook.domain.GitHub;

import lombok.Data;

import java.util.List;

@Data
public class IssueDto {

    private String title;
    private String author;
    private List<LabelDto> labels;
}
