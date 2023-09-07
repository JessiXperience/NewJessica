package org.jessixperience.jessica.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsNotificationsScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.Console;
import java.util.Iterator;
import java.util.Locale;

import static net.minecraft.client.gui.DrawableHelper.drawTexture;

@Nullable
@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    private static final Identifier BACKGROUND = new Identifier("backgrounds/back.jpg");
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject( method = "init", at = @At( "HEAD" ), cancellable = true )
    public void init( CallbackInfo ci ) {
        int y = this.height / 4 + 48;
        int spacingY = 24;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button) -> {
            this.client.setScreen(new SelectWorldScreen(this));
        }).dimensions(this.width / 2 - 100, y, 200, 20).build());
        Tooltip tooltip = Tooltip.of( Text.empty() );
        boolean bl = true;
        ((ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.multiplayer"), (button) -> {
            Screen screen = this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
            this.client.setScreen((Screen)screen);
        }).dimensions(this.width / 2 - 100, y + spacingY * 1, 200, 20).tooltip(tooltip).build())).active = bl;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.options"), (button) -> {
            this.client.setScreen(new OptionsScreen(this, this.client.options));
        }).dimensions(this.width / 2 - 100, y + 72 + 12, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.quit"), (button) -> {
            this.client.scheduleStop();
        }).dimensions(this.width / 2 + 2, y + 72 + 12, 98, 20).build());

        ci.cancel();
    }

    @Inject( method = "render", at = @At( "HEAD" ), cancellable = true )
    public void render( MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci ) {
        RenderSystem.setShaderTexture( 0, BACKGROUND );
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.2F);
        drawTexture(matrices, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);

        float g = 1.0F;
        int i = MathHelper.ceil(g * 255.0F) << 24;
        if ((i & -67108864) != 0) {

            super.render(matrices, mouseX, mouseY, delta);
            Iterator var9 = this.children().iterator();

            while (var9.hasNext()) {
                Element element = (Element) var9.next();
                if (element instanceof ClickableWidget) {
                    ((ClickableWidget) element).setAlpha(1F);
                }
            }
        }

        ci.cancel();
    }

}
