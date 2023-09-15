package org.jessixperience.jessica.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import org.jessixperience.jessica.NewJessica;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject( method = "applyDamage", at = @At( "HEAD" ) )
    public void onDamage(DamageSource source, float amount, CallbackInfo ci) {
        NewJessica.INSTANCE.getLogger().info( "Damage tooked" );
    }

}
