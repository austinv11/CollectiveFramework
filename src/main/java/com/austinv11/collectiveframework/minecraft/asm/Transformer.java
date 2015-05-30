package com.austinv11.collectiveframework.minecraft.asm;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class Transformer implements IClassTransformer, Opcodes {
	
	public static boolean didCheck = false;
	public static boolean isDev = false;
	
	@Override
	public byte[] transform(String className, String newClassName, byte[] byteCode) {
		if (className.equals("net.minecraft.client.gui.FontRenderer")) {
			CollectiveFramework.LOGGER.info("Applying color code patch");
			return transformFontRenderer(byteCode);
		} else if (className.equals("net.minecraft.client.renderer.entity.RenderEnchantmentTable")) {
			CollectiveFramework.LOGGER.info("Hooking into RenderEnchantmentTable#renderTileEntityAt(Lnet/minecraft/tileentity/TileEntityEnchantmentTable;DDDF)V");
			return transformRenderEnchantmentTable(byteCode);
		}
		return byteCode;
	}
	
	private byte[] transformRenderEnchantmentTable(byte[] byteCode) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(byteCode);
		classReader.accept(classNode, 0);
		for (MethodNode m : classNode.methods)
			if (checkDeobfAndObfNames(m.name, "renderTileEntityAt", "func_147500_a"))
				if (m.desc.contains("TileEntityEnchantmentTable")) {
					Iterator<AbstractInsnNode> nodes = m.instructions.iterator();
					while (nodes.hasNext()) {
						AbstractInsnNode node = nodes.next();
						if (node.getOpcode() == GETSTATIC) {
							InsnList instructions = new InsnList();
							instructions.add(new VarInsnNode(ALOAD, 1));
							instructions.add(new MethodInsnNode(INVOKESTATIC, "com/austinv11/collectiveframework/minecraft/hooks/ClientHooks", "getBookTexture", "(Lnet/minecraft/tileentity/TileEntityEnchantmentTable;)Lnet/minecraft/util/ResourceLocation;", false));
							instructions.add(new VarInsnNode(ASTORE, 7));
							instructions.add(new VarInsnNode(ALOAD, 7));
							m.instructions.insertBefore(node, instructions);
							m.instructions.remove(node);
						}
					}
				}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(writer);
		return writer.toByteArray();
	}
	
	//Adding a useful feature from the 'Essentials' bukkit plugin, allowing the use of '&' for color codes
	//See http://ess.khhq.net/mc/
	private byte[] transformFontRenderer(byte[] byteCode) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(byteCode);
		classReader.accept(classNode, 0);
		for (MethodNode m : classNode.methods) {
			if (checkDeobfAndObfNames(m.name, "renderStringAtPos", "func_78255_a")) {
				InsnList instructions = new InsnList();
				instructions.add(new VarInsnNode(ALOAD, 1));
				instructions.add(new MethodInsnNode(INVOKESTATIC, "com/austinv11/collectiveframework/minecraft/hooks/ClientHooks", "getStringToRender", "(Ljava/lang/String;)Ljava/lang/String;", false));
				instructions.add(new VarInsnNode(ASTORE, 1));
				m.instructions.insert(instructions);
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
	
	private boolean checkDeobfAndObfNames(String input, String deobf, String obf) {
		if (!didCheck && (input.equals(deobf) || input.equals(obf))) {
			didCheck = true;
			if (input.equals(deobf)) {
				isDev = true;
			} else {
				isDev = false;
			}
		}
		return input.equals(deobf) || input.equals(obf);
	}
}
