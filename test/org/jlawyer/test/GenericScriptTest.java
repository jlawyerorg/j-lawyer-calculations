/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlawyer.test;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jens
 */
public class GenericScriptTest {

    public GenericScriptTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMetadata() {

        try {

            GroovyScriptEngine e = new GroovyScriptEngine("src");

            Binding bind = new Binding();
            e.run("org/jlawyer/calculations/rvg/rvg_meta.groovy", bind);
            Assert.assertNotNull(bind.getVariable("name"));
            Assert.assertNotNull(bind.getVariable("description"));
            Assert.assertNotNull(bind.getVariable("version"));
            Assert.assertNotNull(bind.getVariable("updated"));
            Assert.assertNotNull(bind.getVariable("author"));
            Assert.assertNotNull(bind.getVariable("supportedPlaceHolders"));

//        Class scriptClass = new GroovyScriptEngine("src").loadScriptByName("org/jlawyer/calculations/rvg/rvg_meta.groovy");
//        Object scriptInstance = scriptClass.newInstance();
//        Object returnVal=scriptClass.getDeclaredMethod("hello_world", new Class[]{}).invoke(scriptInstance, new Object[]{});
        } catch (Throwable t) {
            t.printStackTrace();
            Assert.fail(t.getMessage());
        }
    }

    @Test
    public void testUi() {
        try {
            //GroovyScriptEngine e=new GroovyScriptEngine("/home/jens/dev/projects/experiments/src/scripting/");
            GroovyScriptEngine e = new GroovyScriptEngine("src");

            Binding bind = new Binding();
            e.run("org/jlawyer/calculations/rvg/rvg_ui.groovy", bind);
            Object scriptPanel = bind.getVariable("SCRIPTPANEL");
            Assert.assertNotNull(scriptPanel);
            Assert.assertTrue(scriptPanel instanceof JPanel);
            //groovyPanel.setSize(this.pnlScriptTarget.getWidth(), this.pnlScriptTarget.getHeight());
            //this.pnlScriptTarget.add(groovyPanel);
            //this.jScrollPane1.setViewportView(groovyPanel);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
