package com.dyj.libapt.annotation.handle;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public interface AnnotationHandler {
    void attachProcessingEnvironment(ProcessingEnvironment env);

    Map<String, List<Element>> handleAnnotation(RoundEnvironment env);
}
