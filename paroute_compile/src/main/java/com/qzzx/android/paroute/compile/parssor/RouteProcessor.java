package com.qzzx.android.paroute.compile.parssor;

import com.google.auto.service.AutoService;
import com.qzzx.android.aroute.annotation.PRoute;
import com.qzzx.android.aroute.annotation.enums.RouteType;
import com.qzzx.android.aroute.annotation.mode.PRoteMate;
import com.qzzx.android.paroute.utils.Constant;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Administrator on 2018/8/6 0006.
 */


@AutoService(Processor.class)
@SupportedOptions("moduleName")
@SupportedAnnotationTypes({"com.qzzx.android.aroute.annotation.PRoute"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouteProcessor extends AbstractProcessor {
    /**
     * 用于创建文件
     */
    Filer filer;
    /**
     * 判断类型
     */
    Types types;
    /**
     * 创建class
     */
    Elements elements;

    String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();

        types = processingEnv.getTypeUtils();

        elements = processingEnv.getElementUtils();

        // Attempt to get user configuration [moduleName]
        Map<String, String> options = processingEnv.getOptions();
        if (MapUtils.isNotEmpty(options)) {
            moduleName = options.get(Constant.KEY_MODULE_NAME);
        }

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.out.println("start-----------------------------");
        if (CollectionUtils.isNotEmpty(annotations)) {
            try {
                Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(PRoute.class);
                parseRoute(set);
            } catch (Exception ex) {
                System.out.println("44444444444444444444444444444");
                ex.printStackTrace();
                System.out.print(ex.getMessage());
                return true;
            }
        }
        System.out.print("end-----------------------------");
        return false;
    }

    private void parseRoute(Set<? extends Element> set) throws IOException {
        System.out.println("111111111111111111111");
        if (CollectionUtils.isNotEmpty(set)) {
            System.out.println("2222222222222222222222222");

            System.out.println("set=================" + set.size());
            TypeMirror ty_Activity = elements.getTypeElement(Constant.ACTIVITY).asType();

            ClassName pRouteMate = ClassName.get(PRoteMate.class);

            // Interface of ARouter
            TypeElement type_IRouteGroup = elements.getTypeElement(Constant.IROUTERROOT);


            ParameterizedTypeName inputMapTypeOfRoot = ParameterizedTypeName.get(ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ClassName.get(PRoteMate.class));


            ParameterSpec rootParamSpec = ParameterSpec.builder(inputMapTypeOfRoot, "atlas").build();

            MethodSpec.Builder loadIntoMehodOfBuild = MethodSpec.methodBuilder(Constant.METHOD_LOAD_INTO)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(rootParamSpec);

            System.out.println("6666666666666666");
            for (Element elemet : set) {


                TypeMirror typeMirror = elemet.asType();
                PRoute proute = elemet.getAnnotation(PRoute.class);
                System.out.println("777777777777777");
                //     PRoteMate pRoteMate = new PRoteMate(proute, elemet, RouteType.ACTIVITY, null);
                System.out.println("888888888888888888888866666");

                loadIntoMehodOfBuild.addStatement("atlas.put($S,$T.build( $S,$T.class,"
                                + proute.stringNameId() +
                                "," + proute.imageId() + "))",
                        proute.path(),
                        pRouteMate,
                        proute.path(),
                        ClassName.get((TypeElement) elemet));
                System.out.println("999999999999999999999999");


            }
            String groupFileName = Constant.NAME_OF_GROUP + 11111111;

            JavaFile.builder(Constant.PACKAGE_OF_GENERATE_FILE, TypeSpec.classBuilder(groupFileName)
                    .addJavadoc("注释")
                    .addSuperinterface(ClassName.get(type_IRouteGroup))
                    .addMethod(loadIntoMehodOfBuild.build()).build()
            )
                    .build().writeTo(filer);
        }
    }
}
