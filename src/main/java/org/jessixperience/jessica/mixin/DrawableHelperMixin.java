package org.jessixperience.jessica.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DrawableHelper.class)
public class DrawableHelperMixin {

    @Shadow @Final public static Identifier OPTIONS_BACKGROUND_TEXTURE = new Identifier( "backgrounds/back.jpg" );

    @Shadow @Final public static Identifier LIGHT_DIRT_BACKGROUND_TEXTURE = new Identifier( "backgrounds/back.jpg" );

}
