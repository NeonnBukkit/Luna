package lunadevs.luna.mcleaks;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import lunadevs.luna.gui.button.ExpandButton;
import lunadevs.luna.login.GuiAltManager;
import lunadevs.luna.main.Parallaxa;

public class GuiRedeemToken
  extends GuiScreen
{
  private final boolean sessionRestored;
  private final String message;
  private GuiTextField tokenField;
  private GuiButton restoreButton;
  public final GuiScreen parent;
  
  public GuiRedeemToken(GuiScreen parent, boolean sessionRestored)
  {
    this(parent, sessionRestored, null);
  }
  
  public GuiRedeemToken(GuiScreen parent, boolean sessionRestored, String message)
  {
    this.parent = parent;
    this.sessionRestored = sessionRestored;
    this.message = message;
  }
  
  public void updateScreen()
  {
    this.tokenField.updateCursorCounter();
  }
  
  public void initGui()
  {
    Keyboard.enableRepeatEvents(true);
    
    this.restoreButton = new ExpandButton(0, this.width / 2 - 150, this.height / 4 + 96 + 18, 128, 20, 
      this.sessionRestored ? "Session restored!" : "Restore Session");
    this.restoreButton.enabled = (MCLeaks.savedSession != null);
    this.buttonList.add(this.restoreButton);
    this.buttonList.add(new ExpandButton(1, this.width / 2 - 18, this.height / 4 + 96 + 18, 168, 20, "Redeem Token"));
    this.buttonList.add(new ExpandButton(2, this.width / 2 - 150, this.height / 4 + 120 + 18, 158, 20, "Get Token"));
    this.buttonList.add(new ExpandButton(3, this.width / 2 + 12, this.height / 4 + 120 + 18, 138, 20, "Cancel"));
    
    this.tokenField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 128, 200, 20);
    this.tokenField.setFocused(true);
  }
  
  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (button.enabled) {
      if (button.id == 0)
      {
        MCLeaks.remove();
        SessionManager.setSession(MCLeaks.savedSession);
        Parallaxa.ircManager.changeNick(MCLeaks.savedSession.getUsername());
        MCLeaks.savedSession = null;
        Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(this.parent, true));
      }
      else if (button.id == 1)
      {
        if (this.tokenField.getText().length() != 16)
        {
          Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(this.parent, false, 
            ChatColor.RED + "The token has to be 16 characters long!"));
          return;
        }
        button.enabled = false;
        button.displayString = "Please wait ...";
        
        ModApi.redeem(this.tokenField.getText(), new Callback()
        {
          public void done(Object o)
          {
            if ((o instanceof String))
            {
              Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(GuiRedeemToken.this.parent, false, ChatColor.RED + (String)o));
              return;
            }
            if (MCLeaks.savedSession == null) {
              MCLeaks.savedSession = Minecraft.getMinecraft().getSession();
            }
            RedeemResponse response = (RedeemResponse)o;
            
            MCLeaks.refresh(response.getSession(), response.getMcName());
            Minecraft.getMinecraft().displayGuiScreen(new GuiRedeemToken(GuiRedeemToken.this.parent, false, 
              ChatColor.GREEN + "Your token was redeemed successfully!"));
            Parallaxa.ircManager.changeNick(response.getMcName());
          }
        });
      }
      else if (button.id == 2)
      {
        try
        {
          Class<?> oclass = Class.forName("java.awt.Desktop");
          Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
          oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, 
            new Object[] { new URI("https://mcleaks.net/") });
        }
        catch (Throwable throwable)
        {
          throwable.printStackTrace();
        }
      }
      else if (button.id == 3)
      {
    	  mc.displayGuiScreen(new GuiAltManager());
      }
    }
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    this.tokenField.textboxKeyTyped(typedChar, keyCode);
    if (keyCode == 15) {
      this.tokenField.setFocused(!this.tokenField.isFocused());
    }
    if ((keyCode == 28) || (keyCode == 156)) {
      actionPerformed((GuiButton)this.buttonList.get(1));
    }
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    this.tokenField.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    Parallaxa.fontRenderer50.drawCenteredString(ChatColor.WHITE + "- " + ChatColor.AQUA + "MCLeaks" + ChatColor.WHITE + 
      "." + ChatColor.AQUA + "net " + ChatColor.WHITE + "-", this.width / 2, (int) 17.0F, 16777215);
    Parallaxa.fontRenderer50.drawCenteredString("Free minecraft accounts", this.width / 2, (int) 32.0F, 16777215);
    
    Parallaxa.fontRenderer50.drawCenteredString("Status:", this.width / 2, (int) 68.0F, 16777215);
    Parallaxa.fontRenderer50.drawCenteredString(MCLeaks.getStatus(), this.width / 2, (int) 90.0F, 16777215);
    
    Parallaxa.fontRenderer.drawString("Token", this.width / 2 - 100, 115.0F, 10526880);
    if (this.message != null) {
    	Parallaxa.fontRenderer50.drawCenteredString(this.message, this.width / 2, (int) 158.0F, 16777215);
    }
    this.tokenField.drawTextBox();
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
