package com.coon.coon_auto_builder.service.dto;

import com.coon.coon_auto_builder.data.dto.BuildDTO;
import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Data
public class MailReportDTO {

    @NonNull
    private String to;
    @NonNull
    private String packageName;
    @NonNull
    private String ref;

    private boolean successs;

    private Map<String, BuildDTO> results = new HashMap<>();

    public void addBuild(String erlang, BuildDTO build) {
        results.put(erlang, build);
    }

    public void calculateSuccess() {
        successs = true;
        for (BuildDTO build : results.values())
            if (!build.isResult()) {
                successs = false;
                break;
            }
    }

    public String getSubject() {
        if (successs) {
            return "Coon build for " + packageName + " " + ref + " succeed";
        } else
            return "Coon build for " + packageName + " " + ref + " failed";
    }

    @Override
    public String toString() {
        return "MailReportDTO{" +
                "to='" + to + '\'' +
                ", packageName='" + packageName + '\'' +
                ", ref='" + ref + '\'' +
                ", successs=" + successs +
                '}';
    }
}
