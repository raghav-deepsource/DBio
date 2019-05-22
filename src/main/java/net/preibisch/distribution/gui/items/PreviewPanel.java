package main.java.net.preibisch.distribution.gui.items;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.net.preibisch.distribution.gui.GUIConfig;
import main.java.net.preibisch.distribution.tools.config.Config;

public class PreviewPanel extends JPanel {
	private static final long serialVersionUID = -5153593379781883390L;

	private BlocksCanvas canvas;
	
	public PreviewPanel() {
		super();
		setLayout(new GridLayout());
		canvas = new BlocksCanvas();
		canvas.setSize(new Dimension(GUIConfig.PREVIEW_PANEL_WIDTH + 50, GUIConfig.PREIVIEW_PANEL_HEIGHT + 50));
		canvas.setPreferredSize(new Dimension(GUIConfig.PREVIEW_PANEL_WIDTH, GUIConfig.previewPreferedHeight + 50));
		JScrollPane scrollFrame = new JScrollPane(canvas);
		canvas.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension(GUIConfig.PREVIEW_PANEL_WIDTH, GUIConfig.PREIVIEW_PANEL_HEIGHT));
		add(scrollFrame);
	}

	public void updateCanvas() {
		canvas.update(Config.getDataPreview().getBlocksPreview());
		revalidate();
		repaint();
	}

}
