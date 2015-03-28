package com.austinv11.collectiveframework.minecraft.asm;

import com.austinv11.collectiveframework.minecraft.event.EventHandler;
import com.austinv11.collectiveframework.utils.ReflectionUtils;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class EarlyTransformer {
	
	//Dynamic registry related fields
	public static List<String> configClasses = new ArrayList<String>();
	public static List<String> eventHandlerClasses = new ArrayList<String>();
	
	public byte[] transform(String className, String newClassName, byte[] byteCode) {
		boolean scanClass = true;
		try {
			scanClass = !ReflectionUtils.classImplementsInterface(Class.forName(className), IIgnored.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (scanClass) { //Dynamic registering
			try {
				scanForConfig(className);
				scanForEventHandler(className);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return byteCode;
	}
	
	private void scanForConfig(String className) throws ClassNotFoundException {
		if (Class.forName(className).isAnnotationPresent(com.austinv11.collectiveframework.minecraft.config.Config.class))
			configClasses.add(className);
	}
	
	private boolean methodContainsAnnotation(MethodNode methodNode, Class<? extends Annotation> annotationClass) {
		for (AnnotationNode annotationNode : methodNode.visibleAnnotations)
			if (annotationNode.desc.equals(("L"+annotationClass.getName()+";").replace(".", "/")))
				return true;
		return false;
	}
	
	private void scanForEventHandler(String className) throws ClassNotFoundException {
		if (Class.forName(className).isAnnotationPresent(EventHandler.class))
			eventHandlerClasses.add(className);
	}
}
