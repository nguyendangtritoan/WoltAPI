package com.example.restapi.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Sections {
    private List<Section> sections;

    public Sections(List<Section> sections) {
        this.sections = sections;
    }

    public Sections() {
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
