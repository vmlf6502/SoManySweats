package com.replaymod.sms.util;

import io.github.notenoughupdates.moulconfig.gui.GuiElement;
import io.github.notenoughupdates.moulconfig.gui.GuiScreenElementWrapper;
import io.github.notenoughupdates.moulconfig.gui.MouseEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class LunarCompatGuiWrapper extends GuiScreenElementWrapper {
    public LunarCompatGuiWrapper(GuiElement element) {
        super(element);
    }

    @Override
    public void handleMouseInput() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int i = Mouse.getEventX() * sr.getScaledWidth() / Display.getWidth();
        int j = sr.getScaledHeight() - Mouse.getEventY() * sr.getScaledHeight() / Display.getHeight() - 1;
        if (Mouse.getEventButton() != -1) {
            element.mouseInput(i, j, new MouseEvent.Click(Mouse.getEventButton(), Mouse.getEventButtonState()));
        }
        if (Mouse.getEventDWheel() != 0) {
            element.mouseInput(i, j, new MouseEvent.Scroll((float) Mouse.getEventDWheel()));
        }
        if (Mouse.getEventDX() != 0 || Mouse.getEventDY() != 0) {
            element.mouseInput(i, j, new MouseEvent.Move((float) Mouse.getEventDX(), (float) Mouse.getEventDY()));
        }
    }
}