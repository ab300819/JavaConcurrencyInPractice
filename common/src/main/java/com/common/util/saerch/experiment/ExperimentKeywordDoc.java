package com.common.util.saerch.experiment;

import com.common.util.saerch.Doc;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExperimentKeywordDoc implements Doc {
    private static final long serialVersionUID = -7933661190476270871L;

    private String id;
    private String baseword;
}
