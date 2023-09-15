package org.jessixperience.jessica.mixin;

import net.minecraft.client.MinecraftClient;
import org.jessixperience.jessica.NewJessica;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class WindowMixin
{
    @Inject( method = "getWindowTitle", at = @At("HEAD"), cancellable = true )
    public void titleRewrite(CallbackInfoReturnable<String> cir ) {
        cir.setReturnValue( NewJessica.MOD_ID + " " + NewJessica.RELEASE );
        cir.cancel();
    }
}
