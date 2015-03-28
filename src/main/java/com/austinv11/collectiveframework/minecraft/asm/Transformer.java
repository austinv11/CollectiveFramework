package com.austinv11.collectiveframework.minecraft.asm;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class Transformer implements IClassTransformer, Opcodes {
	
	@Override
	public byte[] transform(String className, String newClassName, byte[] byteCode) {
		if (className.equals("net.minecraft.client.gui.FontRenderer") && Config.applyColorPatch) {
			CollectiveFramework.LOGGER.info("Applying color code patch");
			return transformFontRenderer(byteCode);
		}
		return byteCode;
	}
	
	//Adding a useful feature from the 'Essentials' bukkit plugin, allowing the use of '&' for color codes
	//See http://ess.khhq.net/mc/
	private byte[] transformFontRenderer(byte[] byteCode) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(byteCode);
		classReader.accept(classNode, 0);
		for (MethodNode m : classNode.methods) {
			if (m.name.equals("renderStringAtPos")) {
				InsnList instructions = new InsnList();
				instructions.add(new VarInsnNode(ALOAD, 1));
				instructions.add(new MethodInsnNode(INVOKESTATIC, "com/austinv11/collectiveframework/minecraft/utils/Colors", "replaceAlternateColorChar", "(Ljava/lang/String;)Ljava/lang/String;", false));
				instructions.add(new VarInsnNode(ASTORE, 1));
				m.instructions.insert(instructions);
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
}
