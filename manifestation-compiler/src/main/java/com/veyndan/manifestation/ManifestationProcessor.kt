package com.veyndan.manifestation

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation

/**
 * This correctly writes out a file to:
 * Manifestation/app/build/generated/source/kapt/debug/com/veyndan/manifestation/AndroidManifest.xml
 *
 * when a class is annotated with @Activity.
 * The main thing problem is when I delete Manifestation/app/src/main/AndroidManifest.xml
 * the build fails. I want Android Studio/Gradle (don't know which one) to see the generated file.
 * I don't know if AndroidManifest.xml needs to be in a different location in the build folder.
 * Otherwise could I write a Gradle script that moves the generated AndroidManifest.xml to the
 * normal position (i.e. app/src/main/AndroidManifest.xml).
 * Or is the whole idea of generating the AndroidManifest.xml using annotations not possible?
 * I don't have any gut feeling about this. The main thing will be to understand the Android build
 * system so I know at what point the AndroidManifest.xml is being referenced in relation to other
 * parts of the build system.
 */
class ManifestationProcessor : AbstractProcessor() {

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Initialised ManifestationProcessor")
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(Activity::class.java.name)

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        val elements = env.getElementsAnnotatedWith(Activity::class.java)

        if (elements.isNotEmpty()) {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "${elements.size}")
            elements.forEach { processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "$it") }
            val fileObject = processingEnv.filer.createResource(StandardLocation.SOURCE_OUTPUT, "com.veyndan.manifestation", "AndroidManifest.xml")
            fileObject.openWriter().use {
                it.write(manifest { }.render())
            }
        }
        return true
    }
}
