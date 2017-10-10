package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage additional model registrations, just implement {@link ModelRegistrar}
 */
public class ModelManager {

    private static List<ModelRegistrar> modelRegistrars = new ArrayList<>();


    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        for (ModelRegistrar modelRegistrar : modelRegistrars)
            modelRegistrar.registerModels(event.getModelRegistry());
    }

    /**
     * Registers an object which requires textures/sprites to be registered
     * @param modelRegistrar The object
     */
    public static void register(ModelRegistrar modelRegistrar) {
        modelRegistrars.add(modelRegistrar);
    }

    /**
     * Implement this to be able to register additional models
     */
    public interface ModelRegistrar {

        /**
         * Called to register models
         * @param modelRegistry The model registry to register models into
         */
        void registerModels(IRegistry<ModelResourceLocation, IBakedModel> modelRegistry);
    }
}