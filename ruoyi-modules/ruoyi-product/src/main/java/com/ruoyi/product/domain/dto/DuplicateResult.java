package com.ruoyi.product.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuplicateResult<T> {
    private List<T> validItems;
    private List<DuplicateRecord<T>> duplicateRecords;
}