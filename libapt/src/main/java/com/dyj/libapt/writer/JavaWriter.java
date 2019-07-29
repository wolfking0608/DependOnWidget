package com.dyj.libapt.writer;

import com.dyj.libapt.annotation.AssetsInjector;
import com.dyj.libapt.annotation.WXShare;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import static javax.lang.model.element.Modifier.PUBLIC;

public class JavaWriter implements AbstractWriter {
    protected ProcessingEnvironment mProcessingEnv;
    protected Filer mFiler;
    protected String APP_ASSETS_PATH = "app/src/main/assets";

    public JavaWriter(ProcessingEnvironment mProcessingEnv) {
        this.mProcessingEnv = mProcessingEnv;
        this.mFiler = mProcessingEnv.getFiler();
    }

    @Override
    public void generate(Map<String, List<Element>> map) {
        for (Map.Entry<String, List<Element>> entry : map.entrySet()) {
            List<Element> elements = entry.getValue();
            for (Element element : elements) {
                WXShare wxShare = element.getAnnotation(WXShare.class);
                if (wxShare != null) {
                    handleAnnotation(wxShare, element);
                }
                AssetsInjector assetsInjector = element.getAnnotation(AssetsInjector.class);
                if (assetsInjector != null) {
                    handleAnnotation(assetsInjector, element);
                }
            }
        }
    }

    private void handleAnnotation(WXShare wxShare, Element element) {
        try {
            String pkgName = wxShare.value();
            TypeSpec typeSpec = TypeSpec.classBuilder("WXEntryActivity")
                    .superclass(ClassName.bestGuess("cn.jiguang.share.wechat.WeChatHandleActivity"))
                    .addModifiers(PUBLIC)
                    .build();
            JavaFile javaFile = JavaFile.builder(pkgName+".wxapi", typeSpec)
                    .addFileComment("These codes are generated by XY automatically. Do not modify!")
                    .build();
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAnnotation(AssetsInjector assetsInjector, Element element) {
        try {
            File assetsFolder = new File(APP_ASSETS_PATH);
            File[] files = assetsFolder.listFiles();
            for (File file : files) {
                if (file.getName().startsWith("X")) {
                    file.delete();
                }
            }
            String filePath = APP_ASSETS_PATH + assetsInjector.value();
            File file = new File(filePath);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
