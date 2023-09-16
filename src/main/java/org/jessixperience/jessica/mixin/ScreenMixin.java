package org.jessixperience.jessica.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin  extends AbstractParentElement implements Drawable
{

    @Shadow public int width;

    @Shadow public int height;

    @Inject( method = "renderBackgroundTexture", at = @At("HEAD"), cancellable = true )
    public void setBlur(MatrixStack matrices, CallbackInfo ci) {
        RenderSystem.setShaderTexture(0, new Identifier( "backgrounds/back.jpg" ) );
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrices, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        ci.cancel();
    }

    @Unique
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {}

    @Unique
    public List<? extends Element> children() {
        return null;
    }
}
