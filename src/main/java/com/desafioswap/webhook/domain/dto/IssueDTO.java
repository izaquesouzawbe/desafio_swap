package com.desafioswap.webhook.domain.dto;

import lombok.Data;
import java.util.List;

@Data
public class IssueDTO {

    private String title;
    private String author;
    private List<LabelDTO> labels;
}
