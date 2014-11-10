/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 *
 * @author grender
 */
public class ClipboardController {

    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void copyToClipboard(String text) {

        StringSelection data = new StringSelection(text);
        clipboard.setContents(data, data);

    }

}
