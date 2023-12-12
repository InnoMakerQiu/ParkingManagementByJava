package ClientSide.SystemClient;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonListenerOfShuttingDownTheSystem implements ActionListener {
    final JFrame parentJFrame;

    ButtonListenerOfShuttingDownTheSystem(JFrame jf) {
        this.parentJFrame = jf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DisplayInterface.shutDownTheParkingSystem();
        parentJFrame.dispose();
        Client.performExitOperations();
    }
}
