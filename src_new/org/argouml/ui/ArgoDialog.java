// $Id$
// Copyright (c) 2003 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

/*
 * ArgoDialog.java
 *
 * Created on 12 June 2003, 00:01
 */

package org.argouml.ui;

import java.awt.Frame;

import javax.swing.JButton;

import org.argouml.application.api.Argo;

/**
 * A dialog with localized buttons.
 *
 * @author Bob Tarling
 */
public class ArgoDialog extends org.argouml.swingext.Dialog {
    
    protected static final String BUNDLE = "Cognitive";
    protected static final String MNEMONIC_KEY_SUFFIX = ".mnemonic";
    
    /**
     * Creates a new ArgoDialog with the default optionType.
     */
    public ArgoDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    /**
     * Creates a new ArgoDialog with the specified optionType.
     */
    public ArgoDialog(Frame owner,
		      String title, int optionType, boolean modal)
    {
        super(owner, title, optionType, modal);
    }
        
    protected void nameButtons() {  
        nameButton(getOkButton(), "button.ok");
        nameButton(getCancelButton(), "button.cancel");
        nameButton(getCloseButton(), "button.close");
        nameButton(getYesButton(), "button.yes");
        nameButton(getNoButton(), "button.no");
        nameButton(getHelpButton(), "button.help");
    }
    
    protected void nameButton(JButton button, String key) {
        if (button != null) {
            button.setText(Argo.localize(BUNDLE, key));
            String mnemonic = Argo.localize(BUNDLE, key + MNEMONIC_KEY_SUFFIX);
            if (mnemonic != null && mnemonic.length() > 0) {
                button.setMnemonic(mnemonic.charAt(0));
            }
        }
    }
}
