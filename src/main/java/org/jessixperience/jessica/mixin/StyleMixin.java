package org.jessixperience.jessica.mixin;

import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Style.class)
public class StyleMixin {
    @Shadow
    public static final Identifier DEFAULT_FONT_ID = new Identifier( "roboto/roboto" );

}
