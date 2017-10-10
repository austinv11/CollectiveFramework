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
	private static int procreationHookCounter = 1;
	
	@Override
	public byte[] transform(String className, String newClassName, byte[] byteCode) {
		if (newClassName.equals("net.minecraft.client.gui.FontRenderer")) {
			CollectiveFramework.LOGGER.info("Applying color code patch");
			return transformFontRenderer(byteCode);
		} else if (newClassName.equals("net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer")) {
			CollectiveFramework.LOGGER.info("Hooking into TileEntityEnchantmentTableRenderer" +
					"#render(Lnet/minecraft/tileentity/TileEntity;DDDFIF)V");
			return transformRenderEnchantmentTable(byteCode);
		} else if (newClassName.equals("net.minecraft.client.gui.GuiMainMenu")) {
			CollectiveFramework.LOGGER.info("Hooking into GuiMainMenu#initGui()V");
			return transformGuiMainMenu(byteCode);
		} else if (newClassName.equals("net.minecraft.entity.ai.EntityAIMate") /*||
					newClassName.equals("net.minecraft.entity.ai.EntityAIVillagerMate")*/) { // TODO villager event
			CollectiveFramework.LOGGER.info("Adding hooks procreation hooks ("+procreationHookCounter+"/2)");
			procreationHookCounter++;
			return transformProcreation(byteCode);
		}
		return byteCode;
	}
	
	private byte[] transformRenderEnchantmentTable(byte[] byteCode) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(byteCode);
		classReader.accept(classNode, 0);
		for (MethodNode m : classNode.methods)
			if (checkDeobfAndObfNames(m.name, "render", "func_192841_a"))
				if (m.desc.contains("TileEntityEnchantmentTable")) {
					Iterator<AbstractInsnNode> nodes = m.instructions.iterator();
					while (nodes.hasNext()) {
						AbstractInsnNode node = nodes.next();
						if (node.getOpcode() == GETSTATIC) {
							InsnList instructions = new InsnList();
							instructions.add(new VarInsnNode(ALOAD, 1));
							instructions.add(new MethodInsnNode(INVOKESTATIC, "com/austinv11/collectiveframework/minecraft/hooks/ClientHooks", "getBookTexture", "(Lnet/minecraft/tileentity/TileEntityEnchantmentTable;)Lnet/minecraft/util/ResourceLocation;", false));
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
	
	private byte[] transformGuiMainMenu(byte[] byteCode) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(byteCode);
		classReader.accept(classNode, 0);
		for (MethodNode m : classNode.methods) {
			if (checkDeobfAndObfNames(m.name, "initGui", "func_73866_w_")) {
				InsnList instructions = new InsnList();
				instructions.add(new MethodInsnNode(INVOKESTATIC, "com/austinv11/collectiveframework/minecraft/hooks/ClientHooks", "click", "()V", false));
				m.instructions.insert(instructions);
				break;
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
	
	private byte[] transformProcreation(byte[] byteCode) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(byteCode);
		classReader.accept(classNode, 0);
		int invokeVirtualCounter = 0;
		for (MethodNode m : classNode.methods) {
			if (checkDeobfAndObfNames(m.name, "spawnBaby", "func_75388_i")) {
				Iterator<AbstractInsnNode> nodes = m.instructions.iterator();
				while (nodes.hasNext()) {
					AbstractInsnNode node = nodes.next();
					if (node.getOpcode() == ASTORE) {
						InsnList instructions = new InsnList();
						instructions.add(new VarInsnNode(ALOAD, 1));
						instructions.add(new VarInsnNode(ALOAD, 0));
						instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/ai/EntityAIMate", "targetMate", "Lnet/minecraft/entity/passive/EntityAnimal;"));
						instructions.add(new VarInsnNode(ALOAD, 0));
						instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/ai/EntityAIMate", "targetMate", "Lnet/minecraft/entity/passive/EntityAnimal;"));
						instructions.add(new MethodInsnNode(INVOKESTATIC, "com/austinv11/collectiveframework/minecraft/hooks/CommonHooks", "procreatePre", "(Lnet/minecraft/entity/EntityAgeable;Lnet/minecraft/entity/passive/EntityAnimal;Lnet/minecraft/entity/passive/EntityAnimal;)Lnet/minecraft/entity/EntityAgeable;", false));
						instructions.add(new VarInsnNode(ASTORE, 1));
						m.instructions.insert(node, instructions);
					} else if (node.getOpcode() == INVOKEVIRTUAL) {
						if (invokeVirtualCounter != 9) {
							invokeVirtualCounter++;
						} else {
							InsnList instructions = new InsnList();
							instructions.add(new VarInsnNode(ALOAD, 1));
							instructions.add(new VarInsnNode(ALOAD, 0));
							instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/ai/EntityAIMate", "targetMate", "Lnet/minecraft/entity/passive/EntityAnimal;"));
							instructions.add(new VarInsnNode(ALOAD, 0));
							instructions.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/ai/EntityAIMate", "targetMate", "Lnet/minecraft/entity/passive/EntityAnimal;"));
							instructions.add(new MethodInsnNode(INVOKESTATIC, "com/austinv11/collectiveframework/minecraft/hooks/CommonHooks", "procreatePost", "(Lnet/minecraft/entity/EntityAgeable;Lnet/minecraft/entity/passive/EntityAnimal;Lnet/minecraft/entity/passive/EntityAnimal;)V", false));
							m.instructions.insert(node, instructions);
						}
					}
				}
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
