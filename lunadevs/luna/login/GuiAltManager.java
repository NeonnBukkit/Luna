package lunadevs.luna.login;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import lunadevs.luna.gui.button.ExpandButton;
import lunadevs.luna.irc.IrcManager;
import lunadevs.luna.main.Parallaxa;
import lunadevs.luna.mcleaks.GuiRedeemToken;
import lunadevs.luna.utils.FileUtils;
import lunadevs.luna.utils.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GuiAltManager extends GuiScreen
{
    private static Minecraft mc = Minecraft.getMinecraft();
    private GuiButton login;
    private GuiButton remove;
    private GuiButton rename;
    private AltLoginThread loginThread;
    private int offset;
    public Alt selectedAlt = null;
    private String status = "�7Waiting...";
    private static final ResourceLocation background = new ResourceLocation("luna/BG1.jpg");
    private static IrcManager irc = Parallaxa.ircManager;
    
    
    public GuiAltManager()
    {
        FileUtils.saveAlts();
    }

    public void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                if (this.loginThread == null)
                {
                    mc.displayGuiScreen(null);
                }
                else
                {
                    if (!this.loginThread.getStatus().equals("\2477Logging in...")) {
                        if (!this.loginThread.getStatus().equals("\2471Do not hit back! \2477Logging in..."))
                        {
                            mc.displayGuiScreen(null);
                            break;
                        }
                    }
                    this.loginThread.setStatus("\2471Do not hit back! \247eLogging in...");
                }
                break;
            case 1:
                String user = this.selectedAlt.getUsername();
                String pass = this.selectedAlt.getPassword();
                this.loginThread = new AltLoginThread(user, pass);
                this.loginThread.start();
                break;
            case 2:
                if (this.loginThread != null) {
                    this.loginThread = null;
                }
                Parallaxa.getAltManager().getAlts().remove(this.selectedAlt);
                this.status = "\2474Removed.";
                this.selectedAlt = null;
                FileUtils.saveAlts();
                break;
            case 3:
                mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            case 4:
                mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            case 5:
                Alt randomAlt =

                        (Alt)Parallaxa.getAltManager().getAlts().get(new Random().nextInt(Parallaxa.getAltManager()
                                .getAlts().size()));
                String user1 = randomAlt.getUsername();
                String pass1 = randomAlt.getPassword();
                this.loginThread = new AltLoginThread(user1, pass1);
                this.loginThread.start();
                break;
            case 6:
                mc.displayGuiScreen(new GuiRenameAlt(this));
                break;
            case 7:
                Alt lastAlt = Parallaxa.getAltManager().getLastAlt();
                if (lastAlt == null)
                {
                    if (this.loginThread == null) {
                        this.status = "\247eThere is no last used alt!";
                    } else {
                        this.loginThread.setStatus("\247eThere is no last used alt!");
                    }
                }
                else
                {
                    String user2 = lastAlt.getUsername();
                    String pass2 = lastAlt.getPassword();
                    this.loginThread = new AltLoginThread(user2, pass2);
                    this.loginThread.start();
                }
                break;
            case 8:
                mc.displayGuiScreen(new GuiRedeemToken(this, false));
                break;
        }
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        if (Mouse.hasWheel())
        {
            int wheel = Mouse.getDWheel();
            if (wheel < 0)
            {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
            else if (wheel > 0)
            {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
        drawDefaultBackground();
        renderBackground(this.width, this.height);
        drawString(this.fontRendererObj, mc.session.getUsername(), 10, 10,
                -7829368);
        drawCenteredString(this.fontRendererObj, "Account Manager - " +
                        Parallaxa.getAltManager().getAlts().size() + " alts",
                this.width / 2, 10, -1);
        drawCenteredString(this.fontRendererObj, this.loginThread == null ? this.status :
                this.loginThread.getStatus(), this.width / 2, 20, -1);
        RenderHelper.drawBorderedRect(50.0F, 33.0F, this.width - 50, this.height - 50, 1.0F,
                -16777216, Integer.MIN_VALUE);
        GL11.glPushMatrix();
        prepareScissorBox(0.0F, 33.0F, this.width, this.height - 50);
        GL11.glEnable(3089);
        int y = 38;
        for (Alt alt : Parallaxa.getAltManager().getAlts()) {
            if (isAltInArea(y))
            {
                String name;
                if (alt.getMask().equals("")) {
                    name = alt.getUsername();
                } else {
                    name = alt.getMask();
                }
                String pass;
                if (alt.getPassword().equals("")) {
                    pass = "\247cCracked";
                } else {
                    pass = alt.getPassword().replaceAll(".", "*");
                }
                if (alt == this.selectedAlt)
                {
                    if ((isMouseOverAlt(par1, par2, y - this.offset)) &&
                            (Mouse.isButtonDown(0))) {
                        RenderHelper.drawBorderedRect(52.0F, y - this.offset - 4,
                                this.width - 52, y - this.offset + 20, 1.0F, -16777216,
                                -2142943931);
                    } else if (isMouseOverAlt(par1, par2, y - this.offset)) {
                        RenderHelper.drawBorderedRect(52.0F, y - this.offset - 4,
                                this.width - 52, y - this.offset + 20, 1.0F, -16777216,
                                -2142088622);
                    } else {
                        RenderHelper.drawBorderedRect(52.0F, y - this.offset - 4,
                                this.width - 52, y - this.offset + 20, 1.0F, -16777216,
                                -2144259791);
                    }
                }
                else if ((isMouseOverAlt(par1, par2, y - this.offset)) &&
                        (Mouse.isButtonDown(0))) {
                    RenderHelper.drawBorderedRect(52.0F, y - this.offset - 4, this.width - 52, y -
                            this.offset + 20, 1.0F, -16777216, -2146101995);
                } else if (isMouseOverAlt(par1, par2, y - this.offset)) {
                    RenderHelper.drawBorderedRect(52.0F, y - this.offset - 4, this.width - 52, y -
                            this.offset + 20, 1.0F, -16777216, -2145180893);
                }
                drawCenteredString(this.fontRendererObj, name, this.width / 2, y - this.offset,
                        -1);
                drawCenteredString(this.fontRendererObj, pass, this.width / 2, y - this.offset +
                        10, 5592405);
                y += 26;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
        if (this.selectedAlt == null)
        {
            this.login.enabled = false;
            this.remove.enabled = false;
            this.rename.enabled = false;
        }
        else
        {
            this.login.enabled = true;
            this.remove.enabled = true;
            this.rename.enabled = true;
        }
        if (Keyboard.isKeyDown(200))
        {
            this.offset -= 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
        else if (Keyboard.isKeyDown(208))
        {
            this.offset += 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
    }

    public void initGui()
    {
        this.buttonList.add(new ExpandButton(0, this.width / 2 + 4 + 76,
                this.height - 24, 75, 20, "Cancel"));
        this.buttonList.add(this.login = new ExpandButton(1, this.width / 2 - 154,
                this.height - 48, 100, 20, "Login"));
        this.buttonList.add(this.remove = new ExpandButton(2, this.width / 2 - 74,
                this.height - 24, 70, 20, "Remove"));
        this.buttonList.add(new ExpandButton(3, this.width / 2 + 4 + 50,
                this.height - 48, 100, 20, "Add"));
        this.buttonList.add(new ExpandButton(4, this.width / 2 - 50,
                this.height - 48, 100, 20, "Direct Login"));
        this.buttonList.add(new ExpandButton(5, this.width / 2 - 154,
                this.height - 24, 70, 20, "Random"));
        this.buttonList.add(this.rename = new ExpandButton(6, this.width / 2 + 4,
                this.height - 24, 70, 20, "Rename"));
        this.buttonList.add(new ExpandButton(7, this.width / 2 - 230,
                this.height - 24, 70, 20, "Last Alt"));
        this.buttonList.add(new ExpandButton(8, this.width / 2 - 230,
                this.height - 48, 70, 20, "MCLeaks"));
        this.login.enabled = false;
        this.remove.enabled = false;
        this.rename.enabled = false;
    }

    private boolean isAltInArea(int y)
    {
        return y - this.offset <= this.height - 50;
    }

    private boolean isMouseOverAlt(int x, int y, int y1)
    {
        return (x >= 52) && (y >= y1 - 4) && (x <= this.width - 52) && (y <= y1 + 20) &&
                (x >= 0) && (y >= 33) && (x <= this.width) && (y <= this.height - 50);
    }

    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (this.offset < 0) {
            this.offset = 0;
        }
        int y = 38 - this.offset;
        for (Alt alt : Parallaxa.getAltManager().getAlts())
        {
            if (isMouseOverAlt(par1, par2, y))
            {
                if (alt == this.selectedAlt)
                {
                    actionPerformed((GuiButton)this.buttonList.get(1));
                    return;
                }
                this.selectedAlt = alt;
            }
            y += 26;
        }
        try
        {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void prepareScissorBox(float x, float y, float x2, float y2)
    {
        int factor = RenderHelper.getScaledRes().getScaleFactor();
        GL11.glScissor((int)(x * factor),
                (int)((RenderHelper.getScaledRes().getScaledHeight() - y2) * factor), (int)((x2 - x) * factor),
                (int)((y2 - y) * factor));
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
    
}
