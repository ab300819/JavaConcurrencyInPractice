package com.common.util.saerch.experiment;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.common.util.saerch.Doc;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author boreas
 */
@Data
public class ExperimentDoc implements Doc {

    private static final long serialVersionUID = 6097697757747390118L;
    @JsonProperty("id")
    private String docId;
    @JsonProperty("title^HL")
    private String highlightedTitle;
    @JsonProperty("body^HL")
    private String highlightedBody;
    @JsonProperty("sourceid")
    private int sourceId;
    private String tag;

    public int getId() {
        return NumberUtils.toInt(StringUtils.substringAfter(docId, "_"));
    }
}
