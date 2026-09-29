package com.example.flymenu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;

public class FlyMenuMod implements ClientModInitializer {
    public static boolean flyEnabled = false;
    public static boolean speedEnabled = false;
    public static boolean noFallEnabled = false;

    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.flymenu.open",
                        InputUtil.Type.KEYSYM,
                        GLFW_KEY_RIGHT_SHIFT,
                        "category.flymenu"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new FlyMenuScreen());
                }
            }

            if (client.player == null) {
                return;
            }

            var abilities = client.player.getAbilities();

            if (flyEnabled) {
                abilities.allowFlying = true;
                abilities.flying = true;

                if (speedEnabled) {
                    abilities.setFlySpeed(0.25F);
                } else {
                    abilities.setFlySpeed(0.05F);
                }
            } else {
                abilities.allowFlying = false;
                abilities.flying = false;
                abilities.setFlySpeed(0.05F);
            }

            if (noFallEnabled) {
                client.player.fallDistance = 0.0F;
                Vec3d velocity = client.player.getVelocity();

                if (velocity.y < 0.0D) {
                    client.player.setVelocity(velocity.x, Math.max(velocity.y, -0.05D), velocity.z);
                }

                if (client.player.isOnGround()) {
                    client.player.setVelocity(client.player.getVelocity().x, 0.0D, client.player.getVelocity().z);
                }
            }
        });
    }

    public static void toggleFly() {
        flyEnabled = !flyEnabled;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }

        if (!flyEnabled) {
            client.player.getAbilities().allowFlying = false;
            client.player.getAbilities().flying = false;
        }
    }

    public static void toggleSpeed() {
        if (!flyEnabled) {
            flyEnabled = true;
        }
        speedEnabled = !speedEnabled;
    }

    public static void toggleNoFall() {
        noFallEnabled = !noFallEnabled;
    }
}
