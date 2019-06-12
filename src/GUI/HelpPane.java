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

public class HelpPane extends JFrame {
	private static final long serialVersionUID = 1L;
	JEditorPane editorPane;

	public HelpPane() {
		super("Clipping Polygons - Help");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 700);

		editorPane = new JEditorPane();
		editorPane.setEditable(false); // Read-only
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
	
	private void showFileOpenError() {
		JOptionPane.showMessageDialog(editorPane, 
				"Unable to open help file.", "File Open Error",
				JOptionPane.ERROR_MESSAGE);
	}
}
