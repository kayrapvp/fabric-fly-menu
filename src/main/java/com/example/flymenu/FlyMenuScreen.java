package com.example.flymenu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class FlyMenuScreen extends Screen {
    public FlyMenuScreen() {
        super(Text.literal("Fly Menu"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 60;

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.literal("Fly: " + (FlyMenuMod.flyEnabled ? "ON" : "OFF")),
                                button -> {
                                    FlyMenuMod.toggleFly();
                                    button.setMessage(Text.literal("Fly: " + (FlyMenuMod.flyEnabled ? "ON" : "OFF")));
                                    updateSpeedButtonText();
                                })
                        .dimensions(centerX - 90, startY, 180, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.literal("5x Speed: " + (FlyMenuMod.speedEnabled ? "ON" : "OFF")),
                                button -> {
                                    FlyMenuMod.toggleSpeed();
                                    button.setMessage(Text.literal("5x Speed: " + (FlyMenuMod.speedEnabled ? "ON" : "OFF")));
                                })
                        .dimensions(centerX - 90, startY + 30, 180, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.literal("No Fall: " + (FlyMenuMod.noFallEnabled ? "ON" : "OFF")),
                                button -> {
                                    FlyMenuMod.toggleNoFall();
                                    button.setMessage(Text.literal("No Fall: " + (FlyMenuMod.noFallEnabled ? "ON" : "OFF")));
                                })
                        .dimensions(centerX - 90, startY + 60, 180, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Kapat"), button -> {
                            if (this.client != null) {
                                this.client.setScreen(null);
                            }
                        })
                        .dimensions(centerX - 90, startY + 100, 180, 20)
                        .build()
        );
    }

    private void updateSpeedButtonText() {
        for (var child : this.children()) {
            if (child instanceof ButtonWidget button) {
                if (button.getMessage().getString().startsWith("5x Speed:")) {
                    button.setMessage(Text.literal("5x Speed: " + (FlyMenuMod.speedEnabled ? "ON" : "OFF")));
                }
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }
}
