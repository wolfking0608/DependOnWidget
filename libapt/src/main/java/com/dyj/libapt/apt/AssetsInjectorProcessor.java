package com.dyj.libapt.apt;

import com.dyj.libapt.annotation.handle.AnnotationHandler;
import com.dyj.libapt.annotation.handle.AssetsInjectorHandler;
import com.dyj.libapt.writer.JavaWriter;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
@AutoService(Processor.class)
@SupportedAnnotationTypes({
                "com.dyj.libapt.annotation.WXShare"
        })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AssetsInjectorProcessor extends AbstractProcessor {

    private List<AnnotationHandler> mHandlers = new ArrayList<>();
    private JavaWriter mWriter;
    private Map<String, List<Element>> map = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        registerHandler(new AssetsInjectorHandler());
        mWriter = new JavaWriter(processingEnv);
    }

    protected void registerHandler(AnnotationHandler handler) {
        mHandlers.add(handler);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (AnnotationHandler handler : mHandlers) {
            handler.attachProcessingEnvironment(processingEnv);
            map.putAll(handler.handleAnnotation(roundEnvironment));
        }
        mWriter.generate(map);
        return true;    //处理完成了，return true就好
    }
}
