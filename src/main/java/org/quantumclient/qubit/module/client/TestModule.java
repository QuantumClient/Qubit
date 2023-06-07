package org.quantumclient.qubit.module.client;

import net.minecraft.block.Blocks;
import org.quantumclient.commons.annoations.Info;
import org.quantumclient.energy.Subscribe;
import org.quantumclient.qubit.Qubit;
import org.quantumclient.qubit.event.EventHudRender;
import org.quantumclient.qubit.event.EventBlockRender;
import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.module.Module;
import org.quantumclient.qubit.settings.CheckSetting;
import org.quantumclient.qubit.settings.ModeSetting;
import org.quantumclient.qubit.settings.numbers.FloatSetting;
import org.quantumclient.qubit.utils.Bind;
import org.quantumclient.qubit.utils.FontUtils;
import org.quantumclient.qubit.utils.annotations.SetCategory;
import org.quantumclient.renderer.Attribute;
import org.quantumclient.renderer.DrawMode;
import org.quantumclient.renderer.VertexDrawer;
import org.quantumclient.renderer.testthing;

import java.awt.*;
import java.awt.Font;


@SetCategory(Category.CLIENT)
@Info("TestModule")
public class TestModule extends Module {

    private final CheckSetting booleanTest = new CheckSetting("BooleanTest", "this is a test",true);
    private final FloatSetting numbertest = new FloatSetting("numbertesta", 4.4f, 4f, 10f, 1);
    private final ModeSetting modTest = new ModeSetting("ModeTest", "Packet", new String[] {"Packet", "Normal"});

    private testthing test;
    VertexDrawer vertexDrawer;
    public TestModule() {
        super();
        addSetting(booleanTest);
        addSetting(numbertest);
        addSetting(modTest);
        //this.bind = new Bind(0, 0, Bind.Type.HOLD);
        //fontRenderer = new FontRenderer(new Font("Comic Sans MS", Font.PLAIN, 256));
        test = new testthing();

    }

    @Override
    protected void onEnable() {
        super.onEnable();
        if (mc.world != null) {

        }

    }

    @Subscribe
    public void onHudRender(EventHudRender event) {

        if (numbertest.getValue() instanceof Float) {
       //     mc.textRenderer.draw(event.getMatrix(), "Hi this is a test", 100, 100, -1);
        }
        //fontRenderer.drawString(event.getMatrix(), "Hi this is a test", 100, 100, false, c);
        FontUtils.drawText(event.getMatrix(), "Hi this is a test", 100, 100, false, Color.CYAN);


        vertexDrawer = new VertexDrawer(DrawMode.QUADS, Attribute.POS, Attribute.COLOR);
        vertexDrawer.vertex(0.5f, -0.5f, 0f).color(Color.BLUE);
        vertexDrawer.vertex(-0.5f, 0.5f, 0f).color(Color.RED);
        vertexDrawer.vertex(0.5f, 0.5f, 0f).color(Color.ORANGE);
        vertexDrawer.vertex(-0.5f, -0.5f, 0f).color(Color.ORANGE);
        vertexDrawer.addElm(2);
        vertexDrawer.addElm(1);
        vertexDrawer.addElm(0);
        vertexDrawer.addElm(0);
        vertexDrawer.addElm(1);
        vertexDrawer.addElm(3);


        vertexDrawer.end();
        vertexDrawer.draw();

    }


}
