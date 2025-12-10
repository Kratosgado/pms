
package com.kratosgado.pms.utils.dto;

import com.kratosgado.pms.utils.enums.ProjectType;

public record ProjectDetailDto(
    String id,
    String name,
    String description,
    ProjectType type,
    int teamSize,
    double budget,
    double progress) {
}
