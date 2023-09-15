package org.jessixperience.jessica.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PressableWidget.class)
public class PressableWidgetMixin extends ClickableWidget
{
    public PressableWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Inject( method = "renderButton", at = @At("HEAD"), cancellable = true )
    public void renderButton( MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci ) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        RenderSystem.setShaderTexture(0, new Identifier("textures/gui/black_square.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        drawNineSlicedTexture(matrices, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
        int i = this.active ? 16777215 : 10526880;
        this.drawScrollableText(matrices, minecraftClient.textRenderer, 2, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {}

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isSelected()) {
            i = 2;
        }

        return 46 + i * 20;
    }
}
