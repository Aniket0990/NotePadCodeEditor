package project1;

import java.awt.FileDialog;

	
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CodeEditorGUI {

	JFrame frame;
	JTextArea textArea;
	JScrollPane scrollPane;
	JMenuBar menu;
	JMenu file, format, edit, commandprompt, languageMenu;
	JMenuItem newItem, newWindowItem, openItem, saveItem, saveAsItem, exitItem;
	JMenuItem wordWrapItem, fontItem, fontSizeItem;
	JMenuItem timesNewRomanItem, arialItem, consolasItem;
	JMenuItem javaItem, cItem, cppItem, pythonItem, htmlItem;
	JMenuItem cmdItem;
	JMenuItem size10, size13, size16, size19, size22, size25;
	int fontSize = 16;
	String fontStyle = "Arial";
	boolean wrap = false;

	String openFileName;
	String openDirectory;

	public CodeEditorGUI() {
		createFrame();
		createtextArea();
		createScrollBar();
		createMenuBar();
		createFileMenuItems();
		createFormatItems();
		createEditItems();
		createCommandItems();
		createLanguageMenu();
	}

	public void createFrame() {
		frame = new JFrame("Notepad code Editor");
		frame.setSize(700, 500);
		frame.setVisible(true);
		createMenuBar();
	}

	public void createtextArea() {
		textArea = new JTextArea();
		frame.add(textArea);
	}

	public void createScrollBar() {
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scrollPane);

	}

	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		file = new JMenu("File");
		menuBar.add(file);

		edit = new JMenu("Edit");
		menuBar.add(edit);

		format = new JMenu("Format");
		menuBar.add(format);

		commandprompt = new JMenu("Command Prompt");
		menuBar.add(commandprompt);

		languageMenu = new JMenu("Languages");
		menuBar.add(languageMenu);

	}

	public void createFileMenuItems() {
		newItem = new JMenuItem("New");
		file.add(newItem);
		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});

		newWindowItem = new JMenuItem("New Window");
		file.add(newWindowItem);
		newWindowItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CodeEditorGUI g2 = new CodeEditorGUI();
				g2.frame.setTitle("Untitled");
			}
		});

		openItem = new JMenuItem("Open");
		file.add(openItem);
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(frame, "Open", FileDialog.LOAD);

				fd.setVisible(true);

				openDirectory = fd.getDirectory();

				openFileName = fd.getFile();

				FileReader fr;

				try {
					fr = new FileReader(openDirectory + openFileName);

					BufferedReader br = new BufferedReader(fr);

					textArea.setText("");

					frame.setTitle(openFileName);

					String data = br.readLine();

					while (data != null) {
						textArea.append(data + "\n");

						data = br.readLine();
					}
					br.close();
					fr.close();
				} catch (FileNotFoundException e1) {
					System.out.println("File path issue");
				} catch (IOException e1) {
					System.out.println("Could not read the data");
				}
			}
		});

		saveItem = new JMenuItem("Save");
		file.add(saveItem);
		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.getTitle().equalsIgnoreCase("untitled")
						|| frame.getTitle().equalsIgnoreCase("Notepad Code Editor")) {
					saveAs();
				} else {
					String path = openDirectory;
					String fileName = openFileName;
//				frame.setTitle(fileName);
					String data = textArea.getText();

					try {
						FileWriter fr = new FileWriter(path + fileName);

						BufferedWriter bw = new BufferedWriter(fr);

						bw.write(data);
						bw.close();
						fr.close();
					} catch (IOException e1) {
						System.out.println("issue with path");
					}
				}
			}

		});

		saveAsItem = new JMenuItem("Save As");
		file.add(saveAsItem);
		saveAsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});

		exitItem = new JMenuItem("Exit");
		file.add(exitItem);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

	}

	public void saveAs() {
		FileDialog fd = new FileDialog(frame, "Save As", FileDialog.SAVE);

		fd.setVisible(true);

		String path = fd.getDirectory();

		String fileName = fd.getFile();

		String data = textArea.getText();

		try {
			FileWriter fr = new FileWriter(path + fileName);

			BufferedWriter bw = new BufferedWriter(fr);

			bw.write(data);
			bw.close();
			fr.close();
			frame.setTitle(fileName);
		} catch (IOException e1) {
			System.out.println("issue with path");
		}
	}

	public void createFormatItems() {
		wordWrapItem = new JMenuItem("Word Wrap : Off");
		wordWrapItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (wrap == false) {
					wordWrapItem.setText("Word Wrap On");
					textArea.setLineWrap(true);
					wrap = true;
				} else {
					wordWrapItem.setText("Word Wrap Off");
					textArea.setLineWrap(false);
					wrap = false;

				}
			}
		});
		format.add(wordWrapItem);

		fontItem = new JMenu("Font");

		timesNewRomanItem = new JMenuItem("Times New Roman");
		fontItem.add(timesNewRomanItem);
		timesNewRomanItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Times New Roman", fontSize);
			}
		});

		arialItem = new JMenuItem("Arial");
		fontItem.add(arialItem);
		arialItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Arial", fontSize);
			}
		});

		consolasItem = new JMenuItem("Consolas");
		fontItem.add(consolasItem);
		consolasItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont("Consolas", fontSize);
			}
		});

		format.add(fontItem);

		fontSizeItem = new JMenu("Font Size");
		format.add(fontSizeItem);
		
		size10 = new JMenuItem("10");
		size10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFont(10);
			}
		});
		fontSizeItem.add(size10);

		size13 = new JMenuItem("13");
		size13.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont(13);
			}
		});
		fontSizeItem.add(size13);

		size16 = new JMenuItem("16");
		size16.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont(16);
			}
		});
		fontSizeItem.add(size16);

		size19 = new JMenuItem("19");
		size19.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont(19);
			}
		});
		fontSizeItem.add(size19);

		size22 = new JMenuItem("22");
		size22.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont(22);
			}
		});
		fontSizeItem.add(size22);

		size25 = new JMenuItem("25");
		size25.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont(25);
			}
		});
		fontSizeItem.add(size25);
	}

	public void setFont(String fontName, int size) {
		switch (fontName) {
		case "Times New Roman":
			textArea.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
			fontStyle = "Times New Roman";
			break;

		case "Arial":
			textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
			fontStyle = "Arial";
			break;

		case "Consolas":
			textArea.setFont(new Font("Consolas", Font.PLAIN, fontSize));
			fontStyle = "Consolas";
			break;
		}
	}

	public void setFont(int size) {
		fontSize = size;
		setFont(fontStyle, fontSize);
	}

	public void setLanguage(String lang) {
		String path = "";
		switch (lang) {
		case "HTML":
		{
			path = "D:\\Qspiders\\Project\\CodeEditor\\htmlFormat.txt";
		}
			break;
		case "Java":
		{
			path = "D:\\Qspiders\\Project\\CodeEditor\\javaFormat.txt";
		}
		break;
		case "C":
		{
			path = "D:\\Qspiders\\Project\\CodeEditor\\cFormat.txt";
		}
		break;
		case "C++":
		{
			path = "D:\\Qspiders\\Project\\CodeEditor\\cppFormat.txt";
		}
		break;
		case "Python":
		{
			path = "D:\\Qspiders\\Project\\CodeEditor\\pythonFormat.txt";
		}
		break;
		}
			try {
				textArea.setText("");
				BufferedReader bf = new BufferedReader(new FileReader(path));
				String line = bf.readLine();
				
				while (line != null) {
					textArea.append(line + "\n");
					System.out.println();
					
					line = bf.readLine();
				}
			} catch (FileNotFoundException e) {
				System.out.println("Issue with path");
			} catch (IOException e) {
				System.out.println("Issue with Format");
			}
			
	}


	public void createLanguageMenu() {
		javaItem = new JMenuItem("Java");
		languageMenu.add(javaItem);
		javaItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("Java");
			}
		});

		cItem = new JMenuItem("C");
		languageMenu.add(cItem);
		cItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("C");
			}
		});

		cppItem = new JMenuItem("C++");
		languageMenu.add(cppItem);
		cppItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("C++");
			}
		});

		pythonItem = new JMenuItem("Python");
		languageMenu.add(pythonItem);
		pythonItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("Python");
			}
		});

		htmlItem = new JMenuItem("HTML");
		htmlItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("HTML");
			}
		});
		languageMenu.add(htmlItem);
	}

	public void createCommandItems() {
		cmdItem = new JMenuItem("Open CMD");
		cmdItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec(new String[] { "cmd", "/K", "start" });
				} catch (IOException e1) {
					System.out.println("issue came in cmd");
				}
			}
		});
		commandprompt.add(cmdItem);
	}

	public void createEditItems() {
		JMenuItem soon = new JMenuItem("Features will implement soon");
//		JMenuItem undo = new JMenuItem("Undo");
//		JMenuItem redu = new JMenuItem("Redu");
//		JMenuItem cut = new JMenuItem("Cut");
//		JMenuItem copy = new JMenuItem("Copy");
//		JMenuItem paste = new JMenuItem("Paste");
//		JMenuItem delete = new JMenuItem("Delete");
//
		edit.add(soon);
//		edit.add(undo);
//		edit.add(redu);
//		edit.add(cut);
//		edit.add(copy);
//		edit.add(paste);
//		edit.add(delete);

	}
}
