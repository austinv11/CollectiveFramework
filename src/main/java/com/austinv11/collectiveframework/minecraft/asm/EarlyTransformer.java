package com.austinv11.collectiveframework.minecraft.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class EarlyTransformer implements IClassTransformer {
	
	//Dynamic registry related fields
	public static List<String> configClasses = new ArrayList<String>();
	public static List<String> eventHandlerClasses = new ArrayList<String>();
	
	public byte[] transform(String className, String newClassName, byte[] byteCode) {
		boolean scanClass = false;
		try {
			scanClass = hasIgnoredAnnotation(byteCode);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (scanClass) { //Dynamic registering
			try {
				ClassNode classNode = new ClassNode();
				ClassReader classReader = new ClassReader(byteCode);
				classReader.accept(classNode, 0);
				scanForConfig(classNode);
				scanForEventHandler(classNode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return byteCode;
	}
	
	private boolean hasIgnoredAnnotation(byte[] byteCode) throws ClassNotFoundException {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(byteCode);
		classReader.accept(classNode, 0);
		if (methodContainsAnnotation(classNode, "com.austinv11.collectiveframework.minecraft.asm.Ignored"))
			return false;
		return true;
	}
	
	private void scanForConfig(ClassNode classNode) throws ClassNotFoundException {
		if (methodContainsAnnotation(classNode, "com.austinv11.collectiveframework.minecraft.config.Config"))
			configClasses.add(classNode.name.replace("/", "."));
	}
	
	private boolean methodContainsAnnotation(ClassNode classNode, Class<? extends Annotation> annotationClass) {
		if (classNode.visibleAnnotations != null && !classNode.visibleAnnotations.isEmpty())
			for (AnnotationNode annotationNode : classNode.visibleAnnotations)
				if (annotationNode.desc.equals(("L"+annotationClass.getName()+";").replace(".", "/")))
					return true;
		return false;
	}
	
	private boolean methodContainsAnnotation(ClassNode classNode, String annotation) {
		if (classNode.visibleAnnotations != null && !classNode.visibleAnnotations.isEmpty())
			for (AnnotationNode annotationNode : classNode.visibleAnnotations)
				if (annotationNode.desc.equals(("L"+annotation+";").replace(".", "/")))
					return true;
		return false;
	}
	
	private void scanForEventHandler(ClassNode classNode) throws ClassNotFoundException {
		if (methodContainsAnnotation(classNode, "com.austinv11.collectiveframework.minecraft.event.EventHandler"))
			eventHandlerClasses.add(classNode.name.replace("/", "."));
	}
}
