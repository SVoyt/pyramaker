/*
 * Copyright 2012 Sergey Voyteshonok info@svoyt.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.svoyt.pyramaker;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

/**
 * Log Form
 * 
 *
 */
public class LogForm {

	private JFrame frame;

	/**
	 * Create form and show log
	 * @param logText log text
	 */
	public LogForm(String logText) {
		initialize(logText);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String logText) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		
		JTextArea textPane = new JTextArea();
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		textPane.setText(logText);
	}

}
