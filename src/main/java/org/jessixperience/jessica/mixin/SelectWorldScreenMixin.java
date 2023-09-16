package org.jessixperience.jessica.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( SelectWorldScreen.class )
public class SelectWorldScreenMixin extends Screen
{
    protected SelectWorldScreenMixin(Text title) {
        super(title);
    }

    @Inject( method = "render", at = @At("HEAD"), cancellable = true )
    public void shouldDrawBackground(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.renderBackground(matrices);
    }

}
