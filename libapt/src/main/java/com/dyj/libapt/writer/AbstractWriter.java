package com.dyj.libapt.writer;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;

public interface AbstractWriter {

    void generate(Map<String, List<Element>> map);
}
