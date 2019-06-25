package GUI;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Help pane for program. Displays the help website in an editor pane.
 * @author Philipp
 * @version 0.1
 *
 */
public class HelpPane extends JFrame {
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Main editor pane where website is displayed.
	 */
	JEditorPane editorPane;

	/**
	 * Creates the help pane and tries to load the help-website.
	 * Shows an error if it cannot be loaded.
	 */
	public HelpPane() {
		super("Clipping Polygons - Help");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1100, 700);

		editorPane = new JEditorPane();
		editorPane.setEditable(false); 
		HTMLEditorKit kit = new HTMLEditorKit();
		editorPane.setEditorKit(kit);

		getContentPane().add(new JScrollPane(editorPane), "Center");
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);
		try {
			editorPane.setPage("file:///" + s + "\\help\\index.html");
		} catch (IOException e) {
			showFileOpenError();
		}

		editorPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent event) {
				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						editorPane.setPage(event.getURL());
					} catch (IOException e) {
						showFileOpenError();
					}
				}      
			}
		});

	}
	
	/**
	 * Displays an error that the help file could not be opened.
	 */
	private void showFileOpenError() {
		JOptionPane.showMessageDialog(editorPane, 
				"Unable to open help file.", "File Open Error",
				JOptionPane.ERROR_MESSAGE);
	}
}
