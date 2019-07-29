package com.dyj.libapt.annotation.handle;

import com.dyj.libapt.annotation.WXShare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class WXShareHandler implements  AnnotationHandler {
    private ProcessingEnvironment processingEnv;

    @Override
    public void attachProcessingEnvironment(ProcessingEnvironment env) {
        this.processingEnv = env;
    }

    @Override
    public Map<String, List<Element>> handleAnnotation(RoundEnvironment env) {
        Map<String, List<Element>> annotationMap = new HashMap<>();
        Set<? extends Element> elementSet = env.getElementsAnnotatedWith(WXShare.class);
        for (Element element : elementSet) {
            VariableElement varElement = (VariableElement) element;
            String className = getEnclosingClassName(varElement);
            List<Element> cacheElements = annotationMap.get(className);
            if (cacheElements == null) {
                cacheElements = new ArrayList<>();
                annotationMap.put(className, cacheElements);
            }
            cacheElements.add(varElement);
        }
        return annotationMap;
    }

    private String getEnclosingClassName(VariableElement varElement) {
        TypeElement typeElement = (TypeElement) varElement.getEnclosingElement();
        String packageName = getPackageName(processingEnv, typeElement);
        return packageName + "." + typeElement.getSimpleName().toString();
    }

    private String getPackageName(ProcessingEnvironment env, Element element) {
        return env.getElementUtils().getPackageOf(element).getQualifiedName().toString();
    }
}
