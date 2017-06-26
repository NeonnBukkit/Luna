package lunadevs.luna.devschangelog;

import org.lwjgl.opengl.GL11;

import lunadevs.luna.Connector.ConnectSettings.ConnectSettings;
import lunadevs.luna.main.Luna;
import lunadevs.luna.utils.faithsminiutils.Wrapper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class Changelog extends GuiScreen {

private GuiScreen parentScreen;
private GuiTextField userNameField;
private GuiBuild2Field build2Field;
private String errorMessage;
private boolean displayError;
private static final ResourceLocation background = new ResourceLocation("luna/bg.png");


public void Login(GuiScreen parentScreen)
{
  this.parentScreen = parentScreen;

}

public void renderBackground(int par1, int par2)
{
    GL11.glDisable(GL11.GL_DEPTH_TEST);
    GL11.glDepthMask(false);
    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glDisable(GL11.GL_ALPHA_TEST);
    this.mc.getTextureManager().bindTexture(background);
    Tessellator var3 = Tessellator.instance;
    var3.getWorldRenderer().startDrawingQuads();
    var3.getWorldRenderer().addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
    var3.getWorldRenderer().addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
    var3.getWorldRenderer().addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
    var3.getWorldRenderer().addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
    var3.draw();
    GL11.glDepthMask(true);
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glEnable(GL11.GL_ALPHA_TEST);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
}

public void initGui(){
	
  int startX = this.width / 2 - 100;
  int width = 200;

  this.userNameField = new GuiTextField(3, mc.fontRendererObj, startX, this.height / 2 - 110, width, 50);
  this.build2Field = new GuiBuild2Field(3, mc.fontRendererObj, startX, this.height / 2 - 35, width, 50);

  this.buttonList.add(new GuiButton(45, this.width / 2 - 102, this.height - 180, 205, 20, "Go back"));
}



public void drawScreen(int mouseX, int mouseY, float partialTicks)
{
    this.renderBackground(this.width, this.height);
   Luna.fontRenderer.drawStringWithShadow("�a+ �7Added this Changelog", this.width / 2 - 115, this.height / 2 - 35, 0xfffff);
   Luna.fontRenderer.drawStringWithShadow("�a+ �7Added Better Whitelist", this.width / 2 - 115, this.height / 2 - 25, 0xfffff);
   Luna.fontRenderer.drawStringWithShadow("�a+ �7Fixed a Lot of Bugs", this.width / 2 - 115, this.height / 2 - 5, 0xfffff);
   Luna.fontRenderer.drawStringWithShadow("�a+ �7Better Tick Aura", this.width / 2 - 115, this.height / 2 - 15, 0xfffff);
   Luna.fontRenderer.drawStringWithShadow("�a+ �7Added Extreme Criticals", this.width / 2 - 115, this.height / 2 - -5, 0xfffff);
   Luna.fontRenderer.drawStringWithShadow("�a+ �7LowHop Now Works on The Latest NCP [Testing]", this.width / 2 - 115, this.height / 2 - -15, 0xfffff);
   Luna.fontRenderer.drawStringWithShadow("�a+ �7Added Particles", this.width / 2 - 115, this.height / 2 - -25, 0xfffff);
   if (this.displayError)
  {
    drawCenteredString(mc.fontRendererObj, this.errorMessage, this.width / 2, 30, 16711680);
  }
<<<<<<< HEAD
   Wrapper.fontRenderer.drawCenteredString("Build " + ConnectSettings.VERSION + ":", this.width / 2 - 100, this.height / 2 - 50, 16777215);
=======
   mc.fontRendererObj.drawString("Build " + Luna.CLIENT_VERSION + ":", this.width / 2 - 100, this.height / 2 - 50, 16777215);
>>>>>>> cc6d8154f09afd46f4b9dbe09dfd445a7b99e8b5
  this.drawGradientRect(12, 12, 12, 12, 0, 0);

 

}
}