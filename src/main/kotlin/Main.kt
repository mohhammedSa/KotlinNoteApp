import java.awt.*
import javax.swing.*

fun main() {
    lookAndFeel()
    SwingUtilities.invokeLater { Note() }
}

fun lookAndFeel() {
    for (info in UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus" == info.name) {
            UIManager.setLookAndFeel(info.className)
            break
        }
    }
}

class Note : JFrame() {
    private val headerPanel: JPanel = JPanel()
    private val addNoteText: JTextArea = JTextArea(3, 20)
    private val addBtn: JButton = JButton("Add Note")
    private val bodyPanel: JPanel = JPanel()

    init {

        title = "Note taking App"
        setSize(500, 700)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = false

        addHeaderPanel()

        bodyPanel.layout = BoxLayout(bodyPanel, BoxLayout.Y_AXIS)

        addBtn.addActionListener {
            val containerPanel = JPanel()
            containerPanel.layout = BoxLayout(containerPanel, BoxLayout.X_AXIS)

            val noteTextLabel = JTextArea()
            noteTextLabel.text = addNoteText.text
            noteTextLabel.isEditable = false

            val scroll = JScrollPane(noteTextLabel)
            scroll.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED


            val editBtn = JButton("Edit")
            editBtn.background = Color.green
            editBtn.addActionListener {
                val newText = inputDialog(noteTextLabel.text)
                noteTextLabel.text = newText
            }

            val delBtn = JButton("X")
            delBtn.background = Color.red
            delBtn.addActionListener {
                bodyPanel.remove(containerPanel)
                bodyPanel.revalidate()
                bodyPanel.repaint()
            }

            containerPanel.add(scroll)
            containerPanel.add(editBtn)
            containerPanel.add(delBtn)

            containerPanel.border = BorderFactory.createEmptyBorder(10, 10, 0, 10)
            containerPanel.maximumSize =
                Dimension(bodyPanel.size.width, containerPanel.preferredSize.height)

            bodyPanel.add(containerPanel)
            bodyPanel.revalidate()
            bodyPanel.repaint()
            addNoteText.text = ""
        }

        add(JScrollPane(bodyPanel), BorderLayout.CENTER)
        isVisible = true
    }

    private fun addHeaderPanel() {
        addBtn.isFocusable = false

        val scroll = JScrollPane(addNoteText)
        scroll.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED

        addNoteText.lineWrap = true
        addNoteText.wrapStyleWord = true
        headerPanel.add(scroll)
        headerPanel.add(addBtn)
        add(headerPanel, BorderLayout.NORTH)
    }

    private fun inputDialog(str: String): String {
        var newText: String = str
        val editDialog = JDialog(this, "Edit Dialog", true)
        editDialog.defaultCloseOperation = DISPOSE_ON_CLOSE
        editDialog.setSize(250, 150)
        editDialog.setLocationRelativeTo(this)
        editDialog.layout = BorderLayout()

        val cancelBtn = JButton("Cancel")
        val okBtn = JButton("OK")
        val buttonsPanel = JPanel(FlowLayout(FlowLayout.RIGHT))
        buttonsPanel.add(cancelBtn)
        buttonsPanel.add(okBtn)

        val textArea = JTextArea(3, 15)
        textArea.text = str
        val scrollArea = JScrollPane(textArea)
        scrollArea.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED

        okBtn.addActionListener {
            if (textArea.text.isNotEmpty() && textArea.text != null) {
                newText = textArea.text
                textArea.text = ""
                editDialog.isVisible = false
            }
        }
        cancelBtn.addActionListener { editDialog.isVisible = false }

        editDialog.add(scrollArea, BorderLayout.CENTER)
        editDialog.add(buttonsPanel, BorderLayout.SOUTH)

        editDialog.isVisible = true
        return newText
    }
}