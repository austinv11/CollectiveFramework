package com.austinv11.collectiveframework.minecraft.asm;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

public class CollectiveFrameworkAccessTransformer extends AccessTransformer {
	
	public CollectiveFrameworkAccessTransformer() throws IOException {
		super("META-INF/cf_at.cfg");
	}
}
