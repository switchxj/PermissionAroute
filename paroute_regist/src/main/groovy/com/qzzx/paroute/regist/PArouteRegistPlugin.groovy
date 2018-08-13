package com.qzzx.paroute.regist;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class PArouteRegistPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        def android = project.getExtensions().getByType(AppExtension);


        def pTransform = new RegistTransform(project);
        android.registerTransform(pTransform);
    }
}
